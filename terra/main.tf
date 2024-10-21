# Kubernetes 프로바이더 설정
provider "kubernetes" {
  config_path = "~/.kube/config"  # 로컬 Kubernetes 설정 파일 경로
}

# Spring Boot Deployment
resource "kubernetes_deployment" "bootdep" {
  metadata {
    name = "bootdep"
  }

  spec {
    replicas = 1

    selector {
      match_labels = {
        app = "bootkube"
      }
    }

    template {
      metadata {
        labels = {
          app = "bootkube"
        }
      }

      spec {
        container {
          image = "bang99/weshareu:latest"
          name  = "boot-container"
          image_pull_policy = "Always"

          port {
            container_port = 8080
          }
        }
      }
    }
  }
}

# Spring Boot Service
resource "kubernetes_service" "bootser" {
  metadata {
    name = "bootser"
  }

  spec {
    selector = {
      app = "bootkube"
    }

    port {
      port        = 8001
      target_port = 8080
    }

    type = "ClusterIP"
  }
}

# Vue.js Deployment
resource "kubernetes_deployment" "vuedep" {
  metadata {
    name = "vuedep"
  }

  spec {
    replicas = 1

    selector {
      match_labels = {
        app = "vuekube"
      }
    }

    template {
      metadata {
        labels = {
          app = "vuekube"
        }
      }

      spec {
        container {
          image = "bang99/weshareu:latest"
          name  = "vue-container"
          image_pull_policy = "Always"

          port {
            container_port = 80
          }
        }
      }
    }
  }
}

# Vue.js Service
resource "kubernetes_service" "vueser" {
  metadata {
    name = "vueser"
  }

  spec {
    selector = {
      app = "vuekube"
    }

    port {
      port        = 8000
      target_port = 80
    }

    type = "ClusterIP"
  }
}

# Ingress
resource "kubernetes_ingress_v1" "weshareu_ingress" {
  metadata {
    name = "weshareu-ingress"
    annotations = {
      "nginx.ingress.kubernetes.io/ssl-redirect" = "false"
      "nginx.ingress.kubernetes.io/rewrite-target" = "/$2"
    }
  }

  spec {
    ingress_class_name = "nginx"

    rule {
      http {
        path {
          path = "/()(.*)$"
          path_type = "ImplementationSpecific"
          backend {
            service {
              name = "vueser"
              port {
                number = 8000
              }
            }
          }
        }
        path {
          path = "/boot(/|$)(.*)$"
          path_type = "ImplementationSpecific"
          backend {
            service {
              name = "bootser"
              port {
                number = 8001
              }
            }
          }
        }
      }
    }
  }
}