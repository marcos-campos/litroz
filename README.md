Descrição
Este é um aplicativo Android que permite a criação, listagem, edição, exclusão e marcação de tarefas como concluídas. O aplicativo segue a arquitetura MVVM (Model-View-ViewModel) para garantir um código modular, testável e fácil de manter. O gerenciamento de dados é feito através do Room para persistência local das tarefas, e a interface é construída com o uso de Fragments, ViewModels e RecyclerViews para exibição de dados.


Funcionalidades
Criar Tarefas: O usuário pode adicionar uma tarefa com um objetivo (descrição) através de um campo de texto.
Listar Tarefas Pendentes: Exibe as tarefas ainda não concluídas.
Concluir Tarefas: Ao marcar uma tarefa como concluída, ela é movida para a lista de tarefas concluídas.
Exibir Detalhes da Tarefa: Ao clicar em uma tarefa, o usuário é levado para uma nova tela onde pode ver mais detalhes da tarefa e também excluí-la.
Excluir Tarefas: O usuário pode excluir uma tarefa tanto na tela de detalhes quanto da lista de tarefas pendentes.
Editar Tarefas: Embora a funcionalidade de edição ainda não tenha sido implementada completamente, o esqueleto do código para edição já está preparado.


Tecnologias Usadas
Kotlin: Linguagem principal utilizada para o desenvolvimento do aplicativo.
Room: Biblioteca para persistência local de dados.
MVVM: Arquitetura utilizada para separar a lógica de negócio da interface do usuário.
Koin: Biblioteca para injeção de dependências.
RecyclerView: Utilizado para listar as tarefas, tanto pendentes quanto concluídas.
ViewModel: Para gerenciar o estado da interface de forma eficiente e desacoplada.
LiveData: Para observar as mudanças de dados e atualizar a interface automaticamente.


Arquitetura
O aplicativo é dividido em várias camadas, seguindo o padrão MVVM:
Model: Define os dados e interage com o banco de dados local através do Room.
View: Composta pelas Activities e Fragments, que exibem a interface de usuário.
ViewModel: Contém a lógica de negócios, gerencia os dados de forma reativa com o uso de LiveData e comunica a View e o Model.


Estrutura de Banco de Dados
O banco de dados é gerido com a biblioteca Room. A tabela tasks contém os seguintes campos:
id: Identificador único da tarefa.
taskName: Nome da tarefa.
isCompleted: Indicador se a tarefa foi concluída.
taskGoal: Objetivo da tarefa.
