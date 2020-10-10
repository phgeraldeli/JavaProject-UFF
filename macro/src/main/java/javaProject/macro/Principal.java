package javaProject.macro;

import corejava.Console;
import javaProject.macro.DAO.perfil.PerfilDAOImpl;
import javaProject.macro.domain.Perfil;
import javaProject.macro.factory.DAOFactory;

import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;
import java.util.List;

public class Principal {
    public static void main(String[] args) {
        String nome;
        String descricao;
        Perfil umPerfil;

        PerfilDAOImpl perfilDAO = DAOFactory.getDAO(PerfilDAOImpl.class);

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

                    descricao = Console.readLine('\n' +
                            "Informe uma descricao para o Perfil: ");

                    if(descricao.isEmpty()) descricao = null;

                    umPerfil = perfilDAO.create(new Perfil(nome, descricao));

                    System.out.println("Perfil Criado: \n" + umPerfil.toString());

                    break;
                }

                case 2: {
                    int resposta = Console.readInt('\n' +
                            "Digite o número do produto que você deseja alterar: ");

                    try {
                        umPerfil = perfilDAO.find((long) resposta);
                    } catch (Exception e) {
                        System.out.println('\n' + e.getMessage());
                        break;
                    }

                    try {
                        int resp = Console.readInt('\n' +
                                "Deseja Alterar:\n [1]: Nome\n [2]: Descricao\n [3]: Nome e descricao");

                        switch (resp) {
                            case 1: {
                                String novoNome = Console.
                                        readLine("Digite o novo nome: ");
                                umPerfil.setNome(novoNome);
                                break;
                            }
                            case 2: {
                                String novaDescricao = Console.
                                        readLine("Digite o nova descricao: ");
                                umPerfil.setDescricao(novaDescricao);
                                break;
                            }
                            case 3: {
                                String novoNome = Console.
                                        readLine("Digite o novo nome: ");
                                String novaDescricao = Console.
                                        readLine("Digite o nova descricao: ");
                                umPerfil.setNome(novoNome);
                                umPerfil.setDescricao(novaDescricao);
                                break;
                            }
                        }
                    } catch (OptimisticLockException e) {
                        System.out.println("\n A operação não foi executada, os dados que você tentou salvar foram modificados por outro usuário");
                    }

                    System.out.println(perfilDAO.update(umPerfil).toString());
                    break;
                }

                case 3: {
                    long resposta = (long) Console.readInt('\n' +
                            "Digite o número do perfil que você deseja remover: ");

                    try {
                        umPerfil = perfilDAO.find(resposta);
                    } catch (EntityNotFoundException e) {
                        System.out.println('\n' + e.getMessage());
                        break;
                    }

                    System.out.println('\n' +
                            "Número = " + umPerfil.getId() +
                            "    Nome = " + umPerfil.getNome());

                    String resp = Console.readLine('\n' +
                            "Confirma a remoção do Perfil?");

                    if (resp.equals("s")) {
                        try {
                            perfilDAO.delete(umPerfil.getId());
                            System.out.println('\n' +
                                    "Produto removido com sucesso!");
                        } catch (EntityNotFoundException e) {
                            System.out.println('\n' + e.getMessage());
                        }
                    } else {
                        System.out.println('\n' +
                                "Produto não removido.");
                    }

                    break;
                }

                case 4: {
                    List<Perfil> lista = perfilDAO.findAll();
                    for (Perfil perfil : lista) {
                        System.out.println(perfil.toString());
                    }
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

