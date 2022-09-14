package com.learnkafka.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learnkafka.domain.LibraryEvent;
import com.learnkafka.domain.LibraryEventType;
import com.learnkafka.producer.LibraryEventProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
public class LibraryEventsController {

    @Autowired
    private LibraryEventProducer libraryEventProducer;

    @PostMapping("/v1/libraryevent")
    public ResponseEntity<LibraryEvent> postLibraryEvent(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException, ExecutionException, InterruptedException {

        libraryEvent.setLibraryEventType(LibraryEventType.NEW);
        log.info("before sendLibraryEvent");
        //libraryEventProducer.sendLibraryEvent(libraryEvent);
        //SendResult<Integer, String> sendResult = libraryEventProducer.sendLibraryEventSynchronous(libraryEvent);
        libraryEventProducer.sendLibraryEvent_Approach2(libraryEvent);
        //log.info("SendResult is {} ", sendResult.toString());
        log.info("after sendLibraryEvent");
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }

}
