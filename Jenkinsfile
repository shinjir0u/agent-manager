pipeline {
	agent any
	stages {
		stage('Build') {
			steps {
				sh 'docker compose down -v'
				sh 'docker compose up --build'
			}
		}
	}
}