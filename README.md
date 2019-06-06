# fire - Deteção de incêndio


Este repositório contém uma aplicação *Android* desenvolvida no âmbito de uma unidade curricular designada **Redes Móveis e Sem Fios**.

A aplicação permite 2 modos de autenticação (modo administrador ou modo utilizador)

O objetivo da aplicação em modo utilizador é permitir-lhe o registo no sistema, onde poderá ter acesso às condições atuais dos seus dispositivos e interagir com os mesmos.

Cada um dos seus dispositivos será um microcontrolador, que interage com sensores de humidade, temperatura, qualidade do ar e atuadores (mini-buzzer e bomba de água). 

Através da aplicação o utilizador poderá realizar funcionalidades em cada um dos dispositivos, como:

- [x] Consultar os valores medidos pelos sensores
- [x] Ligar ou desligar os atuadores
- [x] Desligar o enable do alarme
- [x] Consultar os incêndios detetados por cada um dos dispositivos

O objetivo da aplicação em modo administrador é permitir a gestão do sistema. 

Através da aplicação o administrador poderá:

- [x] Registar um novo dispositivo no sistema que lhe retornará um token a ser fornecido ao comprador do dispositivo
- [x] Alterar os thresholds dos valores dos sensores que indicam incêndio
- [x] Consultar a lista de fogos detetados pelos dispositivos e adicionar uma nota da causa para cada um.
- [x] Remover utilizadores/dispositivos da base de dados

Associado a este repositório estão outros 2 que contém o servidor *web* desenvolvido em *Python* e o código *Arduino* desenvolvido para cada dispositivo.

**Nota final: 20**

Autores: **Rúben Gomes** e **Leandro Almeida**
