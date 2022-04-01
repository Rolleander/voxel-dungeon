#ifdef GL_ES
precision lowp float;
#endif

uniform sampler2D s_texture;
uniform vec3 u_lightPos;
varying vec2 v_texCoord;

uniform float material_shininess;
uniform vec4 material_diffuse; 
uniform vec4 material_specular; 
uniform vec4 scene_ambient_light;
uniform vec4 scene_light;

varying vec3 N;
varying vec3 v;
varying float v_occlusion;
void main()
{
	vec4 color;
	color = texture2D( s_texture, v_texCoord );

 	vec4 finalDiffuse = vec4(0.0);
	vec4 finalSpecular = vec4(0.0);
	
	vec4 ambient = material_diffuse * color;
	vec4 diffuse = color * material_diffuse;
	
	vec3 E = normalize(-v); 
	vec3 Ll = u_lightPos.xyz - v;  
	vec3 L = normalize(Ll);
	vec3 R = normalize(-reflect(L,N));
	float distance = length(Ll)*0.2;
	distance = 0.0;
	float diffuseTerm = clamp(dot(N, L), 0.0,1.0);
	float specularTerm = pow(max(dot(R, E), 0.0), material_shininess);
	specularTerm = diffuseTerm >= 0.0 ? specularTerm : 0.0;
	finalDiffuse += scene_light *diffuseTerm / (1.0+distance);
	finalSpecular += scene_light *specularTerm / (1.0+distance);
	
	vec4 ambientTerm = ambient * scene_ambient_light;
	float occ = min(1.0,v_occlusion+0.3);
	if (occ < 0.2)
	gl_FragColor =  (ambientTerm  + finalDiffuse * diffuse + finalSpecular * material_specular)*0.2;
	else
	gl_FragColor =  (ambientTerm  + finalDiffuse * diffuse + finalSpecular * material_specular)*occ;
}