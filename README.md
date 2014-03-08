exemplo-rh
==========

Exemplo de uma simples aplicação web em Java para gestão de recursos humanos.


##Modelo
O modelo consiste em um relacionamento entre as entidades Departamento e Colaborador, onde deve ser possível realizar a manipulação básicas destas entidades (CRUD).
Neste caso, um colaborador sempre está relacionado a um único departamento, e este último pode estar vazio ou ter vários colaboradores.
Deve ser possível ainda visualizar todos os departamentos vazios, além de contornar a situação da remoção de um departamento com colaboradores.

##Implementação
Inicialmente está disponível um exemplo usando [Spring Framework](rh-spring).
