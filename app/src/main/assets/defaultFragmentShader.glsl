precision mediump float;

varying vec2 textureCoord;
varying vec3 toLightVector;
varying vec3 surfaceNormal;

uniform vec3 lightColor;
uniform sampler2D modelTexture;

vec4 diffuseLight = vec4(1, 1, 1, 1);

void calcDiffuseColor(float ambient) {
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(toLightVector);
    float brightness = max(dot(unitNormal, unitLightVector), ambient);
    diffuseLight = (brightness * (vec4(lightColor, 1.0)));
}

void main() {

    vec4 texture_color = texture2D(modelTexture, textureCoord);
    if (texture_color.a < 0.5)
        discard;

    calcDiffuseColor(0.0);

    vec4 ambient = vec4(1, 1, 1, 1) * 0.1;

    gl_FragColor = (diffuseLight + ambient) * texture_color;
}