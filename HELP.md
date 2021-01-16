http://localhost:8080/swagger-ui.html
http://localhost:8080/dumb

gradle runAppInfrastructureComposeUp
gradle bootRun
gradle runAppInfrastructureComposeDown
gradle -stop

gradle test

cd terraform
terraform init
terraform plan
terraform apply
terraform destroy