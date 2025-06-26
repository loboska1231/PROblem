package org.copyria2.order_service.jobs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.copyria2.order_service.dto.ChangeDto;
import org.copyria2.order_service.entity.ChangeEntity;
import org.copyria2.order_service.mapper.ChangeMapper;
import org.copyria2.order_service.repository.ChangeRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
public class ChangeSchedular {
    private final ChangeRepository changeRepository;
    private final ChangeMapper changeMapper;
    @Qualifier("changeRestClient")
    private final RestClient restClient;
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.DAYS)
    public void getChange(){
        ResponseEntity<ChangeDto[]> json = restClient.get().retrieve().toEntity(ChangeDto[].class);
        List<ChangeEntity> changes = Arrays.stream(json.getBody())
                .map(changeMapper::toChangeEntity)
                .toList();
        changeRepository.deleteAll();
        changeRepository.saveAll(changes);
        log.info("Changes have been saved: {}", json);
    }
}