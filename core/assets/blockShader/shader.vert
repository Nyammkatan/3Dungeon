attribute vec4 a_position;
attribute vec4 a_color;
attribute vec2 a_texCoord0;
uniform mat4 u_projectionViewMatrix;
varying vec4 v_color;
varying vec2 v_texCoords;
varying vec4 v_position;

void main()
{
   v_color = a_color;
   v_color.a = v_color.a * (255.0/254.0);
   v_texCoords = a_texCoord0;
   v_position = a_position;
   gl_Position =  u_projectionViewMatrix * a_position;
}

