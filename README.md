## 📌 프로젝트에 대한 설명

`balenciaga` 서비스는 8개 코디 카테고리(상의, 아우터, 바지, 스니커즈, 가방, 모자, 양말, 액세서리)에서

- 카테고리별 최저가 브랜드·상품 조회
- 단일 브랜드로 전 카테고리 구매 시 최저 총액 브랜드·상품 조회
- 특정 카테고리의 최저가·최고가 브랜드·상품 조회
- 브랜드·상품 CRUD 관리  
  기능을 REST API로 제공합니다.

### 🚩 구현 방향 (설계 의도 및 기술적 결정)

- **레이어드 아키텍처**: Controller ▶ Service ▶ Repository 로 책임 분리
- **DTO 분리**: API 응답/요청 스펙을 도메인 모델과 완전히 분리
- **Cache** 적용: `PriceCacheService`, `CategoryCacheService`로 빈번한 조회 결과 캐싱
- **테스트 커버리지**:
    - 단위 테스트(`src/test/kotlin`)
    - 통합 테스트(`src/integrationTest/kotlin`)

### 🚩 프로젝트 구조 설명

```
├─ src
│  ├─ main
│  │  ├─ kotlin/musinsa/homework
│  │  │  ├─ config            # Spring 설정 (Cache, DataSource 등)
│  │  │  ├─ controller       # REST API Controller
│  │  │  ├─ dto
│  │  │  │  ├─ brand         # 브랜드 관련 DTO
│  │  │  │  ├─ product       # 상품 관련 DTO
│  │  │  │  └─ price         # 가격 조회 관련 DTO
│  │  │  ├─ exception        # 글로벌 예외 처리 및 커스텀 예외
│  │  │  ├─ repository       # Spring Data JPA Repository
│  │  │  ├─ service          # 비즈니스 로직 Service
│  │  │  └─ BalenciagaApplication.kt  # Spring Boot 메인 클래스
│  │  └─ resources
│  │     ├─ application.yml  # 기본 설정 (H2 등)
│  │     └─ data.csv         # 샘플 데이터 시드
│  ├─ test
│  │  └─ kotlin/musinsa/homework  # 단위 테스트
│  └─ integrationTest
│     └─ kotlin/musinsa/homework  # 통합 테스트
└─ build.gradle                  # Gradle Groovy DSL 설정
```

---
## 빌드 및 실행 방법

### 빌드
```bash
./gradlew clean build
```

### 실행
```bash
./gradlew bootRun
```

### H2 콘솔 접속

- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:testdb`
- (Username/Password 기본값: sa / —)

---

## API 엔드포인트

| 구분  | 메서드    | URL                                             | 설명                                          |
|-----|--------|-------------------------------------------------|---------------------------------------------|
| 1   | GET    | `/api/v1/prices/cheapest-products`              | 카테고리별 최저가 상품+총액 조회                          |
| 2   | GET    | `/api/v1/prices/cheapest-brands`                | 단일 브랜드 전 카테고리 최저 총액 조회                      |
| 3   | GET    | `/api/v1/categories/{categoryName}/price-range` | 카테고리명으로 최저가·최고가 브랜드·상품 조회 (카테고리 이름은 영어로 입력) |
| 4-1 | POST   | `/api/v1/brands`                                | 브랜드 생성                                      |
| 4-2 | PATCH  | `/api/v1/brands/{id}`                           | 브랜드 업데이트                                    |
| 4-3 | DELETE | `/api/v1/brands/{id}`                           | 브랜드 삭제                                      |
| 4-4 | POST   | `/api/v1/products`                              | 상품 생성                                       |
| 4-5 | PATCH  | `/api/v1/products/{id}`                         | 상품 업데이트                                     |
| 4-6 | DELETE | `/api/v1/products/{id}`                         | 상품 삭제                                       |

---
## 테스트 실행

- **단위 테스트**

```bash
./gradlew test
```

- **통합 테스트**

```bash
./gradlew integrationTest
```

---

## 추가 정보

- **샘플 데이터**:  
  애플리케이션 시작 시 csv를 파싱하여 자동으로 로드됩니다.
- **캐시 무효화**:  
  상품 추가/수정/삭제 시 `@CacheEvict` 로 캐시를 제거하도록 설계되어 있습니다.
