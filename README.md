# annotated-method-argument-performance-issue

테스트 방법은 다음과 같습니다.
```bash
$ mvn test
```


pom.xml에서 Spring 버전을 변경할 수 있습니다.
```xml
<!--<spring.version>4.2.4.RELEASE</spring.version>-->
<!--<spring.version>4.3.3.RELEASE</spring.version>-->
<!--<spring.version>4.3.4.RELEASE</spring.version>-->
<spring.version>4.3.5.RELEASE</spring.version>
```


AppTests에서 5만번 리퀘스트를 생성합니다.

```java
@Test
public void simple() throws Exception {
    StopWatch watch = new StopWatch();

    watch.start();
    for (int i = 0; i < 50000; i++) {
        if (i%5000 == 0) {
            watch.stop();
            System.out.println(watch.getTotalTimeMillis());
            watch.start();
        }
        this.mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Dooray-App-Key", UUID.randomUUID().toString()))
                .andExpect(status().isOk())
                .andReturn();
    }
    watch.stop();
    System.out.println(watch.getTotalTimeMillis());
}
```
