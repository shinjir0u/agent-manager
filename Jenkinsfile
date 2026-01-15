pipeline {
	agent any
	stages {
		stage('Build') {
			steps {
				sh 'docker compose -d --build up'
			}
		}
	}
}