# 코드 컨벤션
## 1. Controller
- 단건 조회: ##Detail, ##DetailSearch -> /domain/detail/id, /domain/detail/search?
- 리스트 조회: ##Summary, ##SummarySearch -> /domain/summary, /domain/summary/search?
- 추가: ##Save -> /domain
- 수정: ##Modify -> /domain/id
- 삭제: ##Remove -> /domain/id

## 2. Service
- 단건 조회: get##Detail
- 리스트 조회: getPaged##Summary, getSliced##Summary
- 추가: add##
- 수정: update##
- 삭제: remove##

## 3. Repository
- 단건 조회: find##${Entity}, find##Dto
- 리스트 조회: 
  + dto -> findPaged##Summary, findSliced##Summary, 
  + entity -> findPaged##List, findSliced##List
- 추가: insert##
- 수정: update##
- 삭제: delete##


