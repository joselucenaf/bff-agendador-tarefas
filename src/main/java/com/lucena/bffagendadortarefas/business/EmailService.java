package com.lucena.bffagendadortarefas.business;


import com.lucena.bffagendadortarefas.business.dto.out.TarefasDTOResponse;
import com.lucena.bffagendadortarefas.infrastructure.client.EmailClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {


    private final EmailClient emailClient;

    public void enviaEmail(TarefasDTOResponse dto){
        emailClient.enviaEmail(dto);
    }
}
