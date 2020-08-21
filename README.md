# WebService - Análise Dados em Lote - © 2020 South System.
  Projeto em Spring Boot de uma construção WebService voltado à atender o desafio da © 2020 South System <link>https://southsystem.com.br/.
   
  Uma solução criada em Java em formato de WebService executando via scheduler. Atendendo como um sistema de análise de dados, onde o sistema deve ```importar lotes de arquivos, ler e analisar os dados e produzir um arquivo de saída```  como um ```relatório sintético``` . 
  
  De sorte que, inicialmente existem 3 tipos de dados dentro desses arquivos ```(.dat)```, definidos por meio de layouts padrões:
   - ```001 [Layout Cliente]: 001çCPFçNameçSalary```; 
   - ```002 [Layout Vendedor]: 002çCNPJçNameçBusiness Area```; 
   - ```003 [Layout Vendas]: 003çSale IDç[Item ID-Item Quantity-Item Price]çSalesman name```; 

 #### Stack do projeto
  - Escrito em Java 8;
  - Utilizando as facilidades e recursos framework Spring Boot;
  - Lombok na classes para evitar o boilerplate do Java;
  - Boas práticas de programação, utilizando Design Patterns (Builder, Strategy);
  - Testes unitátios (TDD).
   
 #### Visão Geral
  
  A aplicaçao tem como objetivo ler cada tipo de dados adotados há um layout diferente e específico. Processá-los e gerar uma saída via arquivo contendo:
    
   - Quantidade de clientes no arquivo de entrada; 
   - Quantidade de vendedores no arquivo de entrada;
   - O ID registro da venda mais cara (maior valor de venda total);
   - O pior vendedor. 
  
 #### Instruções Inicialização
  
    1. Clone o repositório git@github.com:NecoDan/south-system-spring-analise-dados-dat.git
    
    2. Ou faça o download do arquivo ZIP do projeto em https://github.com/NecoDan/south-system-spring-analise-dados-dat
        
    3. Importar o projeto em sua IDE de preferência (lembre-se, projeto baseado em Spring)
    
    4. Buildar o projeto e executá-lo.
  
 #### Autor e mantenedor do projeto
 - Daniel Santos Gonçalves - Bachelor in Information Systems, Federal Institute of Maranhão - IFMA / Software Developer Fullstack.
 - GitHub: https://github.com/NecoDan
 
 - Linkedin: <link>https://www.linkedin.com/in/daniel-santos-bb072321 
 - Twiter: <link>https://twitter.com/necodaniel. 
