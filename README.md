How to?

Step-1 : Create a Spring Boot Project form https://start.spring.io/

Step-2 : Add dependencies : oauth2-client and spring-gateway

Step-3 : Go to google console and generate client and secret

Step-4 : Add below properties to application.yml file

           spring:
            security:
              oauth2:
                client:
                  registration:
                    google:
                      clientId: replace-your-client-id
                      clientSecret: replace-your-client-secret
                      

Step-5 : Create security config and add the request patterns which need to be authenticated according to your requirement (I have made every api call to be authenticated via google oauth except api endpoint starting with **public** )

          @Bean
          public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception{
              return http
                .authorizeExchange(req->{
                    req.pathMatchers("/public/**").permitAll();
                    req.anyExchange().authenticated();
                })
                .oauth2Login(Customizer.withDefaults())
                .build();
          }

Step-6 : Create GlobalRequestFilter and extract jwt from Authentication object which is present in idToken attribute.
          
          authentication -> {
                OAuth2AuthenticationToken oauth2Auth = (OAuth2AuthenticationToken) authentication;
                String idToken = ((DefaultOidcUser) oauth2Auth.getPrincipal()).getIdToken().getTokenValue();
                exchange.getRequest().mutate().header("Authorization", "Bearer " + idToken);
                return chain.filter(exchange);
          }

Step-7 : Add cloud gateway dependecies in yml file, it will redirect every call to resource app whose path starting with /resource
  
          cloud:
            gateway:
              routes:
                - id: learn-oauth-resource
                  uri: http://localhost:9000
                  predicates:
                    - Path=/resource/**

Step-8 : Go to https://github.com/isarthak/learn-oauth-resource and create a oauth resource 

Step-9 : Start both the apps client and resource service

Congrats! Google oauth2 is implemented   
                      
                  
