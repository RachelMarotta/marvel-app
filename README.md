# Projeto Marvel App

Este é um aplicativo Android que lista os personagens da Marvel utilizando a API da Marvel. Ele implementa a arquitetura MVVM e Clean Architecture, utilizando Kotlin e algumas bibliotecas populares do Android como Retrofit, Glide, e LiveData.

## Pré-requisitos

1. **Credenciais da API Marvel:** Você precisa se cadastrar no site da [API da Marvel](https://developer.marvel.com/) e gerar suas credenciais de autenticação (Public Key e Private Key).
2. **Android Studio:** Certifique-se de ter o Android Studio instalado em sua máquina.

## Configuração do Projeto

### Passo 1: Clonar o repositório

Clone este repositório para sua máquina local utilizando o comando:

```
git clone https://github.com/seu-usuario/seu-repositorio.git
```

### Passo 2: Adicionar as credenciais da API Marvel

Você precisa adicionar suas credenciais da API Marvel no arquivo local.properties do projeto. Este arquivo está localizado na raiz do projeto. Se ele não existir, crie-o. Adicione as seguintes linhas ao arquivo local.properties:

```
marvel_public_key=YOUR_PUBLIC_KEY
marvel_private_key=YOUR_PRIVATE_KEY
```

Substitua YOUR_PUBLIC_KEY e YOUR_PRIVATE_KEY pelas suas chaves da API Marvel.

### Passo 3: Abrir o projeto no Android Studio

- Abra o Android Studio.
- Clique em "Open an existing project".
- Navegue até o diretório onde você clonou o repositório e selecione a pasta do projeto.
- Aguarde o Android Studio sincronizar o projeto e baixar todas as dependências necessárias.

## Estrutura do Projeto

O projeto segue a arquitetura MVVM e Clean Architecture, com as seguintes principais pastas e arquivos:

- data: Contém as implementações da camada de dados, incluindo repositórios, fontes de dados remotas e locais.
- domain: Contém os modelos de domínio e os casos de uso.
- ui: Contém as implementações da camada de apresentação, incluindo ViewModels, Adapters, e Fragments.

## Principais Arquivos

- MarvelService.kt: Define os endpoints da API da Marvel.
- CharacterRepositoryImpl.kt: Implementação do repositório de personagens.
- CharacterViewModel.kt: ViewModel responsável pela lógica de negócios dos personagens.
- CharacterListFragment.kt: Fragment que exibe a lista de personagens.

## Funcionalidades

- Listagem de Personagens: Exibe uma lista de personagens da Marvel.
- Busca por Nome: Permite buscar personagens pelo nome utilizando um campo de busca.
- Favoritar Personagens: Permite favoritar personagens.
- Paginação: Carrega mais personagens à medida que o usuário rola a lista.

## Executando o Projeto
- Certifique-se de que todas as dependências foram instaladas e o projeto foi sincronizado corretamente.
- Conecte um dispositivo Android ou inicie um emulador.
- Clique no botão "Run" no Android Studio para compilar e executar o aplicativo.

## Requisitos

### Home - Characters
- [x] Listagem dos personagens.

- [x] Botão para favoritar nas células.

- [x] Barra de busca para filtrar lista de personagens por nome.

- [x] Interface de lista vazia, erro ou sem internet.

### Detalhes do personagem
- [ ] Botão de favorito.

- [ ] Botão para compartilhar a imagem do personagem.

- [ ] Foto em tamanho maior

- [ ] Descrição (se houver).

## Favoritos
- [ ] Listagem dos personagens favoritados pelo usuário.

- [ ] Interface de lista vazia, erro ou sem internet.

## Testes
- [ ] Testes de interface.

- [ ] Testes unitários.