package com.lucena.bffagendadortarefas.business;

import com.lucena.bffagendadortarefas.business.dto.in.LoginDTORequest;
import com.lucena.bffagendadortarefas.business.dto.out.TarefasDTOResponse;
import com.lucena.bffagendadortarefas.business.enums.StatusNotificacaoEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CronService {

    private final TarefasService tarefasService;
    private final EmailService emailService;
    private final UsuarioService usuarioService;

    @Value("${usuario.email}")
    private String email;
    @Value("${usuario.senha}")
    private String senha;

    //Criar o Cron:
        /* O Scheduled recebe um parametro, como fixos(5min) etc, mas é bom usar as variáveis settadas no application
            @schedule(cron = "${cron.horario}"). Como faz para settar esses horarios no Cron: tem um site para o cron:
            https://docs.oracle.com/cd/E12058_01/doc/doc.1014/e12030/cron_expressions.htm para pegar todas as referêcias
            de horas, dias, ano etc
         */

    @Scheduled(cron = "${cron.horario}")

    public void buscaTarefasProximaHora(){
        String token = login(converterParaRequestDTO());
        log.info("Iniciada a busca de tarefas");
        LocalDateTime horaAtual = LocalDateTime.now();
        LocalDateTime horaFutura = LocalDateTime.now().plusHours(1);
        //Qualquer tarefa que fique entre a hora atual e a hora futura + 1
        //Se agora é 22h, qualquer tarefa entre 22h e 23h
                //Serao permitidas o cadastro de tarefas

        List<TarefasDTOResponse> listaTarefas = tarefasService.buscarTarefasAgendadasPorPeriodo(horaAtual, horaFutura, token);
        log.info("Tarefas encontradas "+ listaTarefas);
        listaTarefas.forEach(tarefa -> {
            emailService.enviaEmail(tarefa);
            log.info("Email enviado para o usuário "+ tarefa.getEmailUsuario());
            tarefasService.alterarStatus(StatusNotificacaoEnum.NOTIFICADO, tarefa.getId(), token);
        });
        log.info("Finalizada a busca e notificação de tarefas");
    }

    public String login(LoginDTORequest dto){
       return usuarioService.loginUsuario(dto);
    }

    public LoginDTORequest converterParaRequestDTO(){
        return LoginDTORequest.builder()
                .email(email)
                .senha(senha)
                .build();
    }
}
