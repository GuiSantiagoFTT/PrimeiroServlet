package com.newti.gui.servlet;

import com.newti.gui.acao.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

//WebServlet(name = "UnicaEntradaServlet", value = "/UnicaEntradaServlet")
public class UnicaEntradaServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String paramAcao = request.getParameter("acao");

        String nome = null;
        String nomeDaClasse = "com.newti.gui.acao." + paramAcao;

        try {
            Class classe = Class.forName(nomeDaClasse);
            Acao acao = (Acao) classe.newInstance();
            nome = acao.executa(request, response);
        }catch(ClassNotFoundException | InstantiationException|IllegalAccessException e){
            throw new ServletException(e);
        }

        String[] tipoEEndereco = nome.split(":");

        if (tipoEEndereco[0].equals("foward")) {
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/view/"+tipoEEndereco[1]);
            rd.forward(request, response);
        } else
            response.sendRedirect(tipoEEndereco[1]);
    }
}

