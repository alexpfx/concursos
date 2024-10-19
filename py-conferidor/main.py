import sys


def main():
    if len(sys.argv) < 3:
        print("Usage: python main.py <gabarito> <respostas>")
        sys.exit(1)

    args = sys.argv[1:]

    # Open gabarito file
    with open(args[0], 'r') as file_gabarito:
        gabarito = file_gabarito.readlines()[0]

    with open(args[1], 'r') as file_respostas:
        respostas = file_respostas.readlines()[0]

    # Check if the number of questions is the same
    if len(gabarito) != len(respostas):
        print("The number of questions is different")
        sys.exit(1)

    score = 0

    for i, g in enumerate(gabarito):
        if g == '\n':
            continue

        g = g.strip().replace('x', 'A').upper()
        r = respostas[i].strip().replace('f', 'E').replace('v', 'C').upper()
        print("Gabarito: ", g, "Resposta: ", r)
        n = get_score(g, r, i + 1)
        score += n
        print("Score: ", score)


def get_score(gabarito, resposta, questao):
    if gabarito == 'A':
        print("Questão", questao, "Anulada")
        return 1
    if resposta == 'X':
        print("Questão", questao, "Sem Resposta")
        return 0

    if gabarito == resposta:
        print("Questão", questao, "Correta")
        return 1
    else:
        print("Questão", questao, "Incorreta")
        return -1


if __name__ == '__main__':
    main()
