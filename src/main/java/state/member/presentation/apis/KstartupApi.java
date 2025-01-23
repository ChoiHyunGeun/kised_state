package state.member.presentation.apis;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import state.member.application.fasade.KstupManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RequestMapping("/kstup")
@Controller
public class KstartupApi {
    private final KstupManager kstupManager;

    public KstartupApi(KstupManager kstupManager) {
        this.kstupManager = kstupManager;
    }

    @GetMapping("/popKeyword")
    public String kstupPopKeywordView() {
        return "/services/kstartup/kstup-keyword";
    }

    @GetMapping("/membAndLogin")
    public String kstupMembLoginView() {
        return "/services/kstartup/kstup-memb-login-cnt";
    }

    /**
     * [년도별, 월별, 일별] 회원 수, 로그인 수
     * @param year
     * @param month
     * @return
     */
    @ResponseBody
    @GetMapping("/getMembAndLoginCnt")
    public Mono<List<List<Map<String, Object>>>> getMembAndLoginCnt(@RequestParam String year, @RequestParam String month) {
        List<Flux<Map<String, Object>>> list = new ArrayList<>();
        list.add(kstupManager.getCurrentMemberCnt().flux());            //현재 회원수
        list.add(kstupManager.getAnnualMemberCnt(year).flux());         //연도별 회원수
        list.add(kstupManager.getMonthlyMemberCnt(year, month).flux()); //월별 회원수
        list.add(kstupManager.getAnnualData());                         //연도별 로그인 수
        list.add(kstupManager.getMonthlyData(year));                    //월별 로그인 수
        list.add(kstupManager.getDailyLoginCnt(year, month));           //일별 로그인 수

        List<Mono<List<Map<String, Object>>>> monoList = list.stream()
                .map(flux -> flux.collectList().defaultIfEmpty(new ArrayList<>()))
                .toList();

        // 병렬 처리로 모든 Mono 완료
        return Mono.zip(monoList, results ->
                Arrays.stream(results)
                        .map(result -> (List<Map<String, Object>>) result)
                        .toList()
        );
    }

    /**
     * [월별, 일별] 로그인 수
     * @param year
     * @param month
     * @return
     */
    @ResponseBody
    @GetMapping("/getMonthlyMembAndLoginCnt")
    public Mono<List<List<Map<String, Object>>>> getMonthlyMembAndLoginCnt(@RequestParam String year, @RequestParam String month) {
        List<Flux<Map<String, Object>>> list = new ArrayList<>();
        list.add(kstupManager.getMonthlyData(year));            //월별 로그인 수
        list.add(kstupManager.getDailyLoginCnt(year, month));   //일별 로그인 수

        List<Mono<List<Map<String, Object>>>> monoList = list.stream()
                .map(flux -> flux.collectList().defaultIfEmpty(new ArrayList<>()))
                .toList();

        // 병렬 처리로 모든 Mono 완료
        return Mono.zip(monoList, results ->
                Arrays.stream(results)
                        .map(result -> (List<Map<String, Object>>) result)
                        .toList()
        );
    }

    /**
     * [일별] 로그인 수
     * @param year
     * @param month
     * @return
     */
    @ResponseBody
    @GetMapping("/getDailyMembAndLoginCnt")
    public Flux<Map<String, Object>> getDailyMembAndLoginCnt(@RequestParam String year, @RequestParam String month) {
        return kstupManager.getDailyLoginCnt(year, month);
    }

    /**
     * [연도별, 월별] 회원 수
     * @param year
     * @param month
     * @return
     */
    @ResponseBody
    @GetMapping("/getMembCnt")
    public Mono<List<Map<String, Object>>> getMembCnt(@RequestParam String year, @RequestParam String month) {
        Mono<Map<String, Object>> annualMemberCnt = kstupManager.getAnnualMemberCnt(year);
        Mono<Map<String, Object>> monthlyMemberCnt = kstupManager.getMonthlyMemberCnt(year, month);

        return Mono.zip(annualMemberCnt, monthlyMemberCnt)
                .map(tuple -> {
                    List<Map<String, Object>> list = new ArrayList<>();
                    list.add(tuple.getT1()); // 연도별 로그인 수
                    list.add(tuple.getT2()); // 월별 로그인 수
                    return list;
                });
    }

    @ResponseBody
    @GetMapping("/getPopKeyword")
    public Mono<List<List<Map<String, Object>>>> getPopKeyword(@RequestParam String year, @RequestParam String month) {
        List<Flux<Map<String, Object>>> list = new ArrayList<>();
        list.add(kstupManager.getAnnualPopKeyword(year));
        list.add(kstupManager.getMonthlyPopKeyword(year, month));
        list.add(kstupManager.getWeeklyPopKeyword(year, month));

        List<Mono<List<Map<String, Object>>>> monoList = list.stream()
                .map(flux -> flux.collectList().defaultIfEmpty(new ArrayList<>()))
                .toList();

        return Mono.zip(monoList, results ->
                Arrays.stream(results)
                        .map(result -> (List<Map<String, Object>>) result)
                        .toList()
        );
    }

    /**
     * 서비스의 년도별, 월별, 주별 데이터를 모두 가져온다
     * @return
     */
    @ResponseBody
    @GetMapping("/getAnnualData")
    public Mono<List<List<Map<String, Object>>>> getEachServiceData(@RequestParam String year, @RequestParam String month) {
        List<Flux<Map<String, Object>>> list = new ArrayList<>();
        list.add(kstupManager.getAnnualData());
        list.add(kstupManager.getMonthlyData(year));
        list.add(kstupManager.getWeeklyData(year, month));

        List<Mono<List<Map<String, Object>>>> monoList = list.stream()
                .map(flux -> flux.collectList().defaultIfEmpty(new ArrayList<>()))
                .toList();

        // 병렬 처리로 모든 Mono 완료
        return Mono.zip(monoList, results ->
                Arrays.stream(results)
                        .map(result -> (List<Map<String, Object>>) result)
                        .toList()
        );
    }

    @ResponseBody
    @GetMapping("/getMonthlyData")
    public Mono<List<List<Map<String, Object>>>> getMonthlyData(@RequestParam String year, @RequestParam String month) {
        List<Flux<Map<String, Object>>> list = new ArrayList<>();
        list.add(kstupManager.getMonthlyData(year));
        list.add(kstupManager.getWeeklyData(year, month));

        // 모든 Flux를 Mono로 변환
        List<Mono<List<Map<String, Object>>>> monoList = list.stream()
                .map(flux -> flux.collectList().defaultIfEmpty(new ArrayList<>()))
                .toList();

        // 병렬 처리로 모든 Mono 완료
        return Mono.zip(monoList, results ->
                Arrays.stream(results)
                        .map(result -> (List<Map<String, Object>>) result)
                        .toList()
        );
    }

    @ResponseBody
    @GetMapping("/getWeeklyData")
    public Mono<List<List<Map<String, Object>>>> getWeeklyData(@RequestParam String year, @RequestParam String month) {
        List<Flux<Map<String, Object>>> list = new ArrayList<>();
        list.add(kstupManager.getWeeklyData(year, month));

        // 모든 Flux를 Mono로 변환
        List<Mono<List<Map<String, Object>>>> monoList = list.stream()
                .map(flux -> flux.collectList().defaultIfEmpty(new ArrayList<>()))
                .toList();

        // 병렬 처리로 모든 Mono 완료
        return Mono.zip(monoList, results ->
                Arrays.stream(results)
                        .map(result -> (List<Map<String, Object>>) result)
                        .toList()
        );
    }
}
