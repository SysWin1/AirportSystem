import pandas as pd
from faker import Faker
import random
from datetime import datetime, timedelta
from dateutil.relativedelta import relativedelta
import uuid
import hashlib

# 원하는 날짜의 데이터 생성
target_date = datetime(2024, 8, 2, 0, 0, 0)

# Faker 인스턴스 생성
fake = Faker()

# 설정할 행 수
num_rows = 144

# 국가 종류 (한국 제외)
safe_country_code = ["USA", "GBR", "JPN", "FRA", "VNM"]
danger_country_code = ["PRK", "IRQ", "RUS"]

# 고유 풀 초기화
fake.unique.clear()


# 고유 여권번호를 생성하는 함수
def generate_unique_passport_numbers(num):
  passport_numbers = set()
  while len(passport_numbers) < num:
    num1 = ''.join([str(random.randint(0, 9)) for _ in range(3)])
    letter = chr(random.randint(65, 90))  # ASCII values for A-Z
    num2 = ''.join([str(random.randint(0, 9)) for _ in range(4)])
    passport_number = f'M{num1}{letter}{num2}'
    passport_numbers.add(passport_number)
  return list(passport_numbers)


passport_numbers = generate_unique_passport_numbers(num_rows)


def generate_expiry_dates(num_rows):
  expiry_dates = []
  for _ in range(3):
    dates_part1 = [fake.date_between(
      start_date=(target_date + relativedelta(days=10)).date(),
      end_date=(
          target_date + relativedelta(years=random.choice([1, 3, 5]))).date()
    ) for _ in range(num_rows // 9)]
    dates_part2 = [fake.date_between(
      start_date=(target_date - relativedelta(days=3)).date(),
      end_date=target_date.date()
    ) + relativedelta(days=3) for _ in range(num_rows // 9)]
    dates_part3 = [fake.date_between(
      start_date=(target_date - relativedelta(years=7)).date(),
      end_date=(target_date - relativedelta(years=5)).date()
    ) for _ in range(num_rows // 9)]
    expiry_dates += dates_part1 + dates_part2 + dates_part3
  return expiry_dates


# 데이터 지정
data = {
  'passport_no': passport_numbers,
  'country_code': ["KOR"] * (num_rows // 3) + [random.choice(safe_country_code)
                                               for _ in
                                               range(num_rows // 3)] + [
                    random.choice(danger_country_code) for _ in
                    range(num_rows // 3)],
  'english_name': [fake.unique.name() for _ in range(num_rows)],
  'gender': [random.choice(["F", "M"]) for _ in range(num_rows)],
  'expiry_date': generate_expiry_dates(num_rows)
}

# 만기일 날짜 형식으로 변경.
data['expiry_date'] = pd.to_datetime(data['expiry_date'])

# 유효한 데이터 생성을 위해 비자 기간을 5년으로 고정.
expiry_years = [5 for _ in range(num_rows)]

# 발행일 계산
data['issue_date'] = [expiry_date - relativedelta(years=years) for
                      expiry_date, years in
                      zip(data['expiry_date'], expiry_years)]

# 생년월일 랜덤
data['birth'] = [fake.date_of_birth(minimum_age=18, maximum_age=90) for _ in
                 range(num_rows)]

PassportInfo_df = pd.DataFrame(data)

# 필요한 데이터 순서로 정렬
PassportInfo_df = PassportInfo_df[
  ['passport_no', 'country_code', 'english_name', 'gender', 'issue_date',
   'expiry_date', 'birth']]

# 데이터 저장
PassportInfo_df.to_csv(f'{target_date.date()}PassportInfo.csv', index=False)

print("PassportInfo CSV file has been created.")


# 고유 신청번호를 생성하는 함수
def generate_apply_no(num):
  apply_nos = set()
  while len(apply_nos) < num:
    u = uuid.uuid4()
    sha256_hash = hashlib.sha256(u.bytes).digest()
    apply_no = ''.join([f'{b:02x}' for b in sha256_hash[:4]])
    apply_nos.add(apply_no)
  return list(apply_nos)


# 비자 타입 종류, 입출국 종류 지정.
visa_types = ['NO_VISA', 'TOURIST_VISA', 'WORK_VISA', 'STUDENT_VISA']
inout_options = ['IN', 'OUT']

# 추가되는 경우의 수를 생성
all_combinations = [(vt, io, 'safe') for vt in visa_types for io in
                    inout_options] + [(vt, io, 'danger') for vt in visa_types
                                      for io in inout_options]

# 생성되는 경우의 수를 일정한 규칙으로 배치
visa_type = []
inout = []
inout_country = []
repeats = num_rows // len(all_combinations)
for _ in range(repeats):
  for combo in all_combinations:
    vt, io, country = combo
    visa_type.append(vt)
    inout.append(io)
    inout_country.append(country)

# 모든 조합을 데이터프레임으로 생성
combinations_df = pd.DataFrame({
  'apply_no': generate_apply_no(num_rows),
  'visa_type': visa_type,
  'inout': inout,
  'safety_type': inout_country
})

# 나머지 고정값 추가
combinations_df['expected_inout_date'] = target_date.strftime('%Y-%m-%d')
combinations_df['approval_status'] = 'DENIED'
combinations_df['reject_reason'] = 'NULL'
combinations_df['created_at'] = [
  fake.date_between(start_date='-1m', end_date='today') for _ in
  range(num_rows)]

# safety_type에 따라 inout_country를 랜덤하게 할당
combinations_df['inout_country'] = combinations_df['safety_type'].apply(
  lambda x: random.choice(safe_country_code) if x == 'safe' else random.choice(
    danger_country_code))

# safety_type 컬럼 제거
combinations_df = combinations_df.drop(columns=['safety_type'])

# 기존 여권데이터와 조합 데이터를 병합
final_df = pd.concat([PassportInfo_df.reset_index(drop=True),
                      combinations_df.reset_index(drop=True)], axis=1)

# 컬럼 순서 재정렬
final_df = final_df[
  ['apply_no', 'passport_no', 'country_code', 'english_name', 'gender',
   'issue_date', 'expiry_date', 'birth', 'visa_type', 'inout', 'inout_country',
   'expected_inout_date', 'approval_status', 'reject_reason', 'created_at']]

# 결과를 CSV로 저장
final_df.to_csv(f'{target_date.date()}AutoApply.csv', index=False)

print("AutoApply CSV file has been created.")

# 테스트를 위한 데이터를 생성

# 추가할 행 수 (576의 배수)
additional_num_rows = num_rows

# 고유 여권번호를 생성하는 함수
additional_passport_numbers = generate_unique_passport_numbers(
  additional_num_rows)

additional_data = {
  'passport_no': additional_passport_numbers,
  'country_code': ["KOR"] * (additional_num_rows // 3) + [
    random.choice(safe_country_code) for _ in
    range(additional_num_rows // 3)] + [random.choice(danger_country_code) for _
                                        in range(additional_num_rows // 3)],
  'english_name': [fake.unique.name() for _ in range(additional_num_rows)],
  'gender': [random.choice(["F", "M"]) for _ in range(additional_num_rows)],
  'expiry_date': generate_expiry_dates(additional_num_rows)
}

additional_data['expiry_date'] = pd.to_datetime(additional_data['expiry_date'])
additional_expiry_years = [5 for _ in range(additional_num_rows)]
additional_data['issue_date'] = [expiry_date - relativedelta(years=years) for
                                 expiry_date, years in
                                 zip(additional_data['expiry_date'],
                                     additional_expiry_years)]
additional_data['birth'] = [fake.date_of_birth(minimum_age=18, maximum_age=90)
                            for _ in range(additional_num_rows)]

additional_df = pd.DataFrame(additional_data)
additional_df = additional_df[
  ['passport_no', 'country_code', 'english_name', 'gender', 'issue_date',
   'expiry_date', 'birth']]
additional_df.to_csv(f'{target_date.date()}PassportInfoForTest.csv',
                     index=False)

print("PassportInfoForTest CSV file has been created.")

# 모든 경우의 수를 차례대로 배치
additional_visa_type = []
additional_inout = []
additional_inout_country = []
additional_repeats = additional_num_rows // len(all_combinations)
for _ in range(additional_repeats):
  for combo in all_combinations:
    vt, io, country = combo
    additional_visa_type.append(vt)
    additional_inout.append(io)
    additional_inout_country.append(country)

# 모든 조합을 데이터프레임으로 생성
additional_combinations_df = pd.DataFrame({
  'apply_no': generate_apply_no(additional_num_rows),
  'visa_type': additional_visa_type,
  'inout': additional_inout,
  'safety_type': additional_inout_country
})

# 나머지 고정된 값 추가
additional_combinations_df['expected_inout_date'] = target_date.strftime(
  '%Y-%m-%d')
additional_combinations_df['approval_status'] = 'DENIED'
additional_combinations_df['reject_reason'] = 'NULL'
additional_combinations_df['created_at'] = [
  fake.date_between(start_date='-1m', end_date='today') for _ in
  range(additional_num_rows)]

# safety_type에 따라 inout_country를 랜덤하게 할당
additional_combinations_df['inout_country'] = additional_combinations_df[
  'safety_type'].apply(
  lambda x: random.choice(safe_country_code) if x == 'safe' else random.choice(
    danger_country_code))

# safety_type 컬럼 제거
additional_combinations_df = additional_combinations_df.drop(
  columns=['safety_type'])

# 기존 데이터와 추가 데이터를 결합
final_df_extended = pd.concat([final_df, pd.concat(
  [additional_df.reset_index(drop=True),
   additional_combinations_df.reset_index(drop=True)], axis=1)],
                              ignore_index=True)

# 결과를 CSV로 저장 (선택 사항)
final_df_extended.to_csv(f'{target_date.date()}AutoApplyForTest.csv',
                         index=False)

print("AutoApplyForTest CSV file has been created.")

# 비자 일수 설정
visa_days = {
  'NO_VISA': 0,
  'TOURIST_VISA': 3,
  'WORK_VISA': 6,
  'STUDENT_VISA': 9
}

# AutoApply.csv 파일을 읽어들임
AutoApply_df = pd.read_csv(f'{target_date.date()}AutoApply.csv')

# expected_inout_date에 visa_type에 따른 일수를 더한 값이 expiry_date보다 작은 경우를 필터링
AutoApply_df['expected_inout_date'] = pd.to_datetime(
  AutoApply_df['expected_inout_date'])

# datetime 형식으로 변경
AutoApply_df['expiry_date'] = pd.to_datetime(AutoApply_df['expiry_date'])

# visa_type에 따른 일수 더하기
AutoApply_df['expected_plus_days'] = AutoApply_df.apply(
  lambda row: row['expected_inout_date'] + timedelta(
    days=visa_days[row['visa_type']]), axis=1)

# 조건에 맞는 행만 선택
FilteredAutoApply_df = AutoApply_df[
  (AutoApply_df['expected_plus_days'] <= AutoApply_df['expiry_date']) &
  (AutoApply_df['visa_type'] != 'NO_VISA') &
  (~AutoApply_df['country_code'].isin(danger_country_code)) &
  (~AutoApply_df['inout_country'].isin(danger_country_code))]

# 필요 없는 컬럼 제거
ImmigrationHistory_df = FilteredAutoApply_df.drop(
  columns=['english_name', 'gender', 'issue_date', 'expiry_date', 'birth',
           'approval_status', 'reject_reason', 'created_at',
           'expected_plus_days'])

# 컬럼명 변경
ImmigrationHistory_df = ImmigrationHistory_df.rename(
  columns={'expected_inout_date': 'inout_date'})

# 필요한 컬럼 순서로 정렬
ImmigrationHistory_df = ImmigrationHistory_df[
  ['apply_no', 'passport_no', 'country_code', 'inout', 'inout_date',
   'visa_type', 'inout_country']]

# 결과를 CSV로 저장
ImmigrationHistory_df.to_csv(f'{target_date.date()}ImmigrationHistory.csv',
                             index=False)

print("ImmigrationHistory CSV file has been created.")
