precision mediump float;

attribute vec3 position;
attribute vec2 textureCoords;
attribute vec3 normal;

uniform mat4 transformationMatrix;
uniform vec3 lightPosition;

varying vec2 textureCoord;
varying vec3 toLightVector;
varying vec3 surfaceNormal;

void main()
{

    vec4 worldCoords = transformationMatrix * vec4(position, 1.0);

    textureCoord = textureCoords * vec2(32, 32);
    surfaceNormal = (vec4(normal, 0.0)).xyz;
    toLightVector = lightPosition - position;

    gl_Position = worldCoords;

}