
## 프로젝트 개요
  #### 작성 필요
---

## 주요 기능

1. **회원 생성**  
   - 새로운 사용자를 등록합니다.
   - 사용자 비밀번호는 암호화(BCrypt)하여 저장됩니다.
2. **회원 조회**  
   - 사용자 이름으로 회원 정보를 조회합니다.

 #### 추가 작성 필요
---

## 기술 스택

- **Java 17**: 최신 Java 언어 기능 활용.
- **Spring Boot 3.1.4**: 애플리케이션 개발의 생산성 향상.
- **Spring Security 6.x**: 사용자 인증 및 권한 관리.
- **Spring Data JPA**: 데이터베이스와의 상호작용.
- **MariaDB**: 관계형 데이터베이스.
- **Lombok**: 보일러플레이트 코드 제거.
- **Gradle**: 프로젝트 빌드 및 의존성 관리.

---
```plaintext
main
└── java
    └── state
        ├── admin                          # 관리자 도메인
        ├── common                         # 공통 설정 및 유틸리티
        │   └── config                     # 설정 파일
        │       └── StateSecurityConfig    # Spring Security 설정
        ├── edu                            # 교육 도메인
        └── member                         # 회원(Member) 도메인
            ├── application                # 애플리케이션 계층
            │   ├── command                # Command 객체 정의
            │   ├── fasade                 # 애플리케이션 파사드
            │   ├── processor              # 비즈니스 로직 처리
            │   └── result                 # 결과 데이터 정의
            ├── domain                     # 도메인 계층
            │   ├── entity                 # 엔티티
            │   ├── exception              # 예외 처리
            │   └── repository             # 도메인 레벨의 Repository 인터페이스
            ├── infrastructure             # 인프라스트럭처 계층
            │   └── JPA                    # JPA 기반 Repository 구현
            └── presentation               # 프레젠테이션 계층 (REST 컨트롤러)
                ├── request                # 요청 DTO
                └── response               # 응답 DTO
```
