package net.bluepoet.exercise;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableAsync
@Slf4j
public class SpringbootExerciseApplication {

    @RestController
    public static class MyController {
        Queue<DeferredResult<String>> results = new ConcurrentLinkedQueue<>();

        @Autowired
        private MyService myService;

        @GetMapping("/calculate")
        public int calculate() {
            return myService.calculate(1, 2);
        }

        @GetMapping("/callable")
        public Callable<String> callable() throws InterruptedException {
            log.info("callable");
            return () -> {
                log.info("async");
                Thread.sleep(2000);
                return "hello";
            };
        }
//		public String callable() throws InterruptedException {
//			log.info("async");
//			Thread.sleep(2000);
//			return "hello";
//		}

        @GetMapping("/dr")
        public DeferredResult<String> dr() {
            log.info("dr");
            DeferredResult<String> dr = new DeferredResult<>(60000L);
            results.add(dr);
            return dr;
        }

        @GetMapping("/emitter")
        public ResponseBodyEmitter emitter() {
            ResponseBodyEmitter emitter = new ResponseBodyEmitter();

            Executors.newSingleThreadExecutor().submit(() -> {
                try {
                    for (int i = 0; i < 50; i++) {
                        emitter.send("<p>stream " + i + "</p>");
                        Thread.sleep(100);
                    }
                }catch(Exception e) {}
            });

            return emitter;
        }

        @GetMapping("/dr/count")
        public String drcount() {
            return String.valueOf(results.size());
        }

        @GetMapping("/dr/event")
        public String drevent(String msg) {
            for(DeferredResult<String> dr : results) {
                dr.setResult("Hello " + msg);
                results.remove(dr);
            }
            return "OK";
         }
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootExerciseApplication.class, args);
    }
}
