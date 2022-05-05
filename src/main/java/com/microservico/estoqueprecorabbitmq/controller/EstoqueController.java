package com.microservico.estoqueprecorabbitmq.controller;

import com.microservico.estoqueprecorabbitmq.service.RabbitMqService;
import constants.RabbitMQConstants;
import dto.EstoqueDTO;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "estoque")
public class EstoqueController {

    @Autowired
    private RabbitMqService rabbitMqService;

    @PutMapping
    private ResponseEntity<?> alteraEstoque(@RequestBody EstoqueDTO estoqueDTO){
        this.rabbitMqService.sendMessage(RabbitMQConstants.FILA_ESTOQUE, estoqueDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
