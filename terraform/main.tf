terraform {
  required_providers {
    docker = { 
    source = "kreuzwerker/docker"
    }
  }
}

provider "docker" {
  host = "unix:///var/run/docker.sock"
}


resource "docker_volume" "db_data" {}

resource "docker_container" "mysql" {
  image    = "mysql:5.7"
  name     = "springboot_gradle_rxjava_webapi_docker_container"
  restart  = "always"
  hostname = "springboot_gradle_rxjava_webapi_docker_container"
  env = [
    "MYSQL_ROOT_PASSWORD=password",
    "MYSQL_DATABASE=dumb_db"
  ]
  mounts {
    type   = "volume"
    target = "/var/lib/mysql"
    source = "db_data"
  }
  ports {
    internal = "3306"
    external = "3306"
  }
}
