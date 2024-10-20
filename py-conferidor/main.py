import sys
from conferidor import Conferidor, Cebraspe

def main():
	if len(sys.argv) < 4:
		print("Usage: python main.py <gabarito> <respostas> <banca>")
		sys.exit(1)
 
	args = sys.argv[1:]

	with open(args[0], 'r') as file_gabarito:
		gabarito = file_gabarito.readlines()[0]

	with open(args[1], 'r') as file_respostas:
		respostas = file_respostas.readlines()[0]

	lg = len(gabarito)
	lr = len(respostas)
	
	
	if lg != lr:
		print(f"O número de items no gabarito [{lg}] é diferente do número de respostas [{lr}] ")
		sys.exit(1)

	banca = args[2]
	conferidor = get_conferidor(banca)

	if conferidor == None:
		print (f"banca inválida {banca}")
		sys.exit(1)


	r = conferidor.execute(gabarito.upper(), respostas.upper())
	print ('Resultado:')
	print (f'\tRespostas corretas {r.corretas}')
	print (f'\tRespostas incorretas {r.incorretas}')
	print (f'\tAnuladas: {r.anuladas}')
	print (f'\tSem resposta: {lg - (r.corretas + r.incorretas + r.anuladas)}')
	
	print (f'\tPontuação: {r.pontuacao}')



def get_conferidor(banca: str) -> Conferidor:
	if 'C' in banca.upper() or 'CEBRASPE' in banca.upper():
		return Cebraspe()
		
		
# 
#     score = 0
# 
#     for i, g in enumerate(gabarito):
#         if g == '\n':
#             continue
# 
#         g = g.strip().replace('x', 'A').upper()
#         r = respostas[i].strip().replace('f', 'E').replace('v', 'C').upper()
#         print("Gabarito: ", g, "Resposta: ", r)
#         n = get_score(g, r, i + 1)
#         score += n
#         print("Score: ", score)
# 
# 
# def get_score(gabarito, resposta, questao):
#     if gabarito == 'A':
#         print("Questão", questao, "Anulada")
#         return 1
#     if resposta == 'X':
#         print("Questão", questao, "Sem Resposta")
#         return 0
# 
#     if gabarito == resposta:
#         print("Questão", questao, "Correta")
#         return 1
#     else:
#         print("Questão", questao, "Incorreta")
#         return -1
# 
# 
if __name__ == '__main__':
    main()
