from abc import ABC, abstractmethod
from typing import Tuple


class Resultado:
	def __init__(self, corretas, incorretas, anuladas, pontuacao):
		self.corretas = corretas
		self.incorretas = incorretas
		self.anuladas = anuladas
		self.pontuacao = pontuacao

		


class Conferidor(ABC):

	def conferir(self, gabarito, respostas) -> Tuple[int, int, int]:
		c = 0
		e = 0
		a = 0
		for i, g in enumerate(gabarito):
			r = respostas[i]

			if g == 'X':
				a += 1
			elif r == 'X':
				continue
			elif r == g:
				c += 1
			else: 
				e += 1

		return [c, e, a]
				
				 
				
				

	def execute(self, gabarito, respostas) -> Resultado:
		c, e, a = self.conferir (gabarito, respostas)

		p = self.calcular(c, e, a)

		return Resultado(c, e, a, p)			



	@abstractmethod
	def calcular(self, c, e, a) -> int:
		pass
		



class Cebraspe(Conferidor):		

	def calcular(self, c, e, a) -> int:
		return c - e
