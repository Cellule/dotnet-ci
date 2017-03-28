import org.dotnet.ci.util.Agents

// Example:
//
// simpleNode('OSX10.12', 'latest-or-auto') { <= braces define the closure, implicitly passed as the last parameter
//     checkout scm
//     sh 'echo Hello world'
// }

// Runs a set of functionality on the default node
// that supports docker.
// Parameters:
//  osName - Docker image to use
//  imageVersion - Version of the OS image.  See Agents.getMachineAffinity
//  body - Closure, see example
def call(String osName, version, Closure body) {
    node (Agents.getAgentLabel(osName, imageVersion)) {
        // Wrap in a try finally that cleans up the workspace
        try {
            // Wrap in the default timeout of 120 mins
            timeout(120) {
                body()
            }
        }
        finally {
            step([$class: 'WsCleanup'])
        }
    }
}