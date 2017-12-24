precision mediump float;

varying vec2 textureCoord;
varying vec3 toLightVector;
varying vec3 surfaceNormal;

uniform vec3 lightColor;
uniform sampler2D modelTexture;

void main() {

    float ambient = 0.0f;
    float brightness = max(dot( normalize(surfaceNormal) , normalize(toLightVector) ), ambient);
    vec4 diffuseLight = (brightness * (vec4(lightColor, 0.0f)));

    gl_FragColor = diffuseLight * texture2D(modelTexture, textureCoord);
}