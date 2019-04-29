precision mediump float;

varying vec2 textureCoord;
varying vec3 toLightVector;
varying vec3 surfaceNormal;

uniform vec3 lightColor;
uniform sampler2D modelTexture;

void main() {

    float ambient = 0.2;
    float brightness = max(dot( normalize(surfaceNormal) , normalize(toLightVector) ), ambient);
    vec4 diffuseLight = (brightness * (vec4(lightColor, 0.0)));

    gl_FragColor = (diffuseLight + vec4(1,1,1,1) * ambient) * texture2D(modelTexture, textureCoord);
}