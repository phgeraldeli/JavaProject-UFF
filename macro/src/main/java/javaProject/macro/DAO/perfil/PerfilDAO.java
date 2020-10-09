package javaProject.macro.DAO.perfil;

import javaProject.macro.DAO.GenericDAO;
import javaProject.macro.domain.Perfil;

import java.util.List;

public interface PerfilDAO extends GenericDAO<Perfil> {
    public List<Perfil> findAll();
}
