#ifdef GL_ES
precision mediump float;
#endif
varying vec4 v_color;
varying vec2 v_texCoords;
varying vec4 v_position;
uniform mat4 u_projectionViewMatrix;
uniform vec3 camCoords;

uniform sampler2D u_texture;

uniform float game_time;

float lerp(float fx1, float fx2, float x1, float x, float x2){
	return fx1+( fx2 - fx1 )*(x - x1)/(x2 - x1);

}

float getColor(float value){
	return clamp(sin(value*game_time*10), 0.0, 1.0);

}

void main()
{
	float d = distance(v_position.xyz, camCoords);
	vec4 color = texture2D(u_texture, v_texCoords);
	//color.rgb = vec3(getColor(color.r), getColor(color.g), getColor(color.b));
	if (d > 2){
		float beginF = 3.0;
		float endF = 5.0;
		d = clamp(d, beginF, endF);
		color.xyz *= lerp(1.0, 0.0, beginF, d, endF);
	}
	gl_FragColor = color;
}
