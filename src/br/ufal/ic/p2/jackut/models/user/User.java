package br.ufal.ic.p2.jackut.models.user;

import java.util.*;

/**
 * Representa a conta básica de um usuário do Jackut.
 */
public class User {

    private String userName;
    private String password;
    private String id;

    /**
     * Cria um usuário vazio para uso por mecanismos de serialização.
     */
    public User(){}

    /**
     * Cria um usuário com login, senha e identificador.
     *
     * @param username login do usuário.
     * @param password senha do usuário.
     * @param id identificador único do usuário.
     */
    public User(String username, String password, String id) {
        this();
        userName = username;
        this.password = password;
        this.id = id;

    }

    /**
     * Valida a senha informada para abertura de sessão.
     *
     * @param password senha informada.
     * @return identificador do usuário se a senha for válida; caso contrário, vazio.
     */
    public Optional<String> validateSection(String password){
         return this.password.matches(password) ? Optional.of(getId()) : Optional.empty();
    }

    /**
     * Retorna o login do usuário.
     *
     * @return login do usuário.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Define o login do usuário.
     *
     * @param userName login do usuário.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Retorna a senha do usuário.
     *
     * @return senha do usuário.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Define a senha do usuário.
     *
     * @param password senha do usuário.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retorna o identificador único do usuário.
     *
     * @return identificador do usuário.
     */
    public String getId() {
        return id;
    }

    /**
     * Define o identificador único do usuário.
     *
     * @param id identificador do usuário.
     */
    public void setId(String id) {
        this.id = id;
    }

}
