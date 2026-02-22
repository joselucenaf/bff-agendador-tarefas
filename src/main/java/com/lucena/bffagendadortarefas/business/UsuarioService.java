package com.lucena.bffagendadortarefas.business;

import com.lucena.bffagendadortarefas.business.dto.EnderecoDTO;
import com.lucena.bffagendadortarefas.business.dto.TelefoneDTO;
import com.lucena.bffagendadortarefas.business.dto.UsuarioDTO;
import com.lucena.bffagendadortarefas.infrastructure.client.UsuarioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioClient client;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        return client.salvaUsuario(usuarioDTO);
    }

    public String loginUsuario(UsuarioDTO usuarioDTO){
        return client.login(usuarioDTO);
    }

    public UsuarioDTO buscarUsuarioPorEmail(String email, String token){
        return client.buscaUsuarioPorEmail(email, token);
    }
    public void deletaUsuarioPorEmail(String email, String token){

        client.deletaUsuarioPorEmail(email, token);
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto){
        return client.atualizDadoUsuario(dto, token);
    }

    public EnderecoDTO atualizaEndereco(Long idEnderco, EnderecoDTO enderecoDTO, String token){
       return client.atualizaEndereco(enderecoDTO, idEnderco, token);
    }

    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO dto, String token){

        return client.atualizaTelefone(dto, idTelefone, token);
    }

    public EnderecoDTO cadastraEndereco(String token, EnderecoDTO dto){
        return client.cadastraEndereco(dto, token);
    }

    public TelefoneDTO cadastraTelefone(String token, TelefoneDTO dto){
        return client.cadastraTelefone(dto, token);
    }

}
