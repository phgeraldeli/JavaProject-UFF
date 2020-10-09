package javaProject.macro.DAO.perfil;

import javaProject.macro.DAO.GenericDAOImpl;
import javaProject.macro.domain.Perfil;
import javaProject.macro.factory.EntityFactory;

import java.util.ArrayList;
import java.util.List;

public class PerfilDAOImpl extends GenericDAOImpl<Perfil> implements PerfilDAO {

    @Override
    public List<Perfil> findAll() {
        try {
            this.em = EntityFactory.criarSessao();

            @SuppressWarnings("unchecked")
            List<Perfil> perfilList = em.createQuery("from Perfil p order by p.id").getResultList();

            return perfilList;
        } finally {
            em.close();
        }
    }
}
