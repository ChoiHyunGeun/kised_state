package state.member.application.processor.dashboard.system1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import state.member.presentation.request.TempRequestDto;

import static state.member.infrastructure.WebClientHandler.getWebClient;

@Slf4j
@Component
public class DashboardGetSystem1DataProcessor {
    @Value("${services.service_1.url}")
    String serviceUrl;
    Flux<TempRequestDto> data;
    public Flux<TempRequestDto> annualExecute() {
        data = null;
        try {
            data = getWebClient(serviceUrl)
                    .get()
                    .uri("/getData")
                    .retrieve()
                    .bodyToFlux(TempRequestDto.class)
                    .onErrorResume(e -> { // 에러 발생 시 null 반환
                        System.err.println("System 1 getData 데이터 호출 실패: " + e.getMessage());
                        return Flux.empty(); // null 반환
                    });
        } catch(Exception e) {
            log.info("External API ERROR : " + e.getMessage());
        }

        return data;
    }

    public Flux<TempRequestDto> monthlyExecute(String year) {
        data = null;
        try {
            data = getWebClient(serviceUrl)
                    .get()
                    .uri("/getMonthlyData?year="+year)
                    .retrieve()
                    .bodyToFlux(TempRequestDto.class)
                    .onErrorResume(e -> { // 에러 발생 시 null 반환
                        System.err.println("System 1 getMonthlyData 데이터 호출 실패: " + e.getMessage());
                        return Flux.empty(); // null 반환
                    });
        } catch(Exception e) {
            log.info("External API ERROR : " + e.getMessage());
        }

        return data;
    }

    public Flux<TempRequestDto> weeklyExecute(String year, String month) {
        data = null;
        try {
            data = getWebClient(serviceUrl)
                    .get()
                    .uri("/getWeeklyData?year="+year+"&month="+month)
                    .retrieve()
                    .bodyToFlux(TempRequestDto.class)
                    .onErrorResume(e -> { // 에러 발생 시 null 반환
                        System.err.println("System 1 getWeeklyData 데이터 호출 실패: " + e.getMessage());
                        return Flux.empty(); // null 반환
                    });
        } catch(Exception e) {
            log.info("External API ERROR : " + e.getMessage());
        }

        return data;
    }
}