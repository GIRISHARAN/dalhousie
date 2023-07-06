#Code based on : https://antonputra.com/google/create-gke-cluster-using-terraform/#create-firewall-in-gcp-using-terraform

provider "google" {
  project = "avid-shape-390123"
  region  = "us-west2"
}

resource "google_container_cluster" "cluster-1" {
  name     = "cluster-1"
  location = "us-west2-a"

  node_pool {
    name               = "my-pool"
    initial_node_count = 1

    node_config {
      machine_type = "e2-standard-2"
      disk_size_gb = 20
      image_type   = "COS_CONTAINERD"
    }
  }
}