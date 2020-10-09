package javaProject.macro;

import corejava.Console;
import javaProject.macro.DAO.perfil.PerfilDAO;
import javaProject.macro.domain.Perfil;
import javaProject.macro.factory.DAOFactory;

public class Principal {
    public static void main(String[] args) {
        String nome;
        Perfil umPerfil;

        PerfilDAO perfilDAO = DAOFactory.getDAO(PerfilDAO.class);
        System.out.println(perfilDAO.toString());
        boolean continua = true;
        while (continua) {
            System.out.println('\n' + "O que você deseja fazer?");
            System.out.println('\n' + "1. Cadastrar um produto");
            System.out.println("2. Alterar um produto");
            System.out.println("3. Remover um produto");
            System.out.println("4. Listar todos os produtos");
            System.out.println("5. Sair");

            int opcao = Console.readInt('\n' +
                    "Digite um número entre 1 e 5:");

            switch (opcao) {
                case 1: {
                    nome = Console.readLine('\n' +
                            "Informe o nome do Perfil: ");

                    umPerfil = new Perfil(nome);

                    perfilDAO.create(umPerfil);

                    System.out.println('\n' + "Produto número " +
                            umPerfil.getId() + " incluído com sucesso!");

                    break;
                }

                case 2: {
                    int resposta = Console.readInt('\n' +
                            "Digite o número do produto que você deseja alterar: ");

                    try {
                        umPerfil = perfilDAO.find(resposta);
                    } catch (Exception e) {
                        System.out.println('\n' + e.getMessage());
                        break;
                    }



                    String novoNome = Console.
                            readLine("Digite o novo nome: ");

                    umPerfil.setNome(novoNome);
                }

                case 3: {
                   break;
                }

                case 4: {
                    break;
                }

                case 5: {
                    continua = false;
                    break;
                }

                default:
                    System.out.println('\n' + "Opção inválida!");
            }
        }
    }
}

