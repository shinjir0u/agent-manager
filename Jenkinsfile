pipeline {
	agent any
	stages {
		stage('Build') {
			steps {
				sh 'docker compose down'
				sh 'docker compose up -d --build'
				
				sh 'sleep 10' 
        		sh 'curl -f http://localhost:8080 || exit 1'
			}
		}
	}
}