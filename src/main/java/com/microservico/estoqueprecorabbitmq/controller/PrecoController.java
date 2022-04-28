package com.microservico.estoqueprecorabbitmq.controller;

import com.microservico.estoqueprecorabbitmq.constants.RabbitMQConstants;
import com.microservico.estoqueprecorabbitmq.dto.PrecoDTO;
import com.microservico.estoqueprecorabbitmq.service.RabbitMqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "preco")
public class PrecoController {

    @Autowired
    private RabbitMqService rabbitMqService;

    @PutMapping
    private ResponseEntity<?> alteraPreco(@RequestBody PrecoDTO precoDTO){
        this.rabbitMqService.sendMessage(RabbitMQConstants.FILA_PRECO, precoDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
