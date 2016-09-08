/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartclass.services;

import context.arch.comm.DataObject;
import context.arch.service.Service;
import context.arch.service.helper.FunctionDescription;
import context.arch.service.helper.FunctionDescriptions;
import context.arch.service.helper.ServiceInput;
import context.arch.widget.Widget;
import java.awt.Desktop;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import smartclass.Professor;
import smartclass.ui.ClassRoomUI;
import smartclass.ui.ProfessorUI;

/**
 *
 * @author Pedro
 */
public class ComputerService extends Service {

    public ComputerService(final Widget widget) {
        super(widget, "ComputerService",
                new FunctionDescriptions() {
            {
                add(new FunctionDescription(
                        "computerControl",
                        "Sets the computer on or off",
                        widget.getNonConstantAttributes()));
            }
        });
    }

    @Override
    public DataObject execute(ServiceInput si) {
        int status = si.getInput().getAttributeValue("status");
        int time = si.getInput().getAttributeValue("time");
        if (status == 1) {
            ProfessorUI professorUI = ProfessorUI.getInstance();
            Professor p = professorUI.getProfessorAttributes(professorUI.getProfessorOfTheTime(time));
            Desktop desktop = Desktop.getDesktop();
            if (p.getSlides() != null && p.getSlides().exists()) {
                try {
                    if(professorUI.getCurOpen().equals("") || !professorUI.getCurOpen().equals(p.getSlides().getName())){
                        professorUI.setCurOpen(p.getSlides().getName());
                        desktop.open(p.getSlides());
                    }
                } catch (IOException ex) {
                    System.out.println("Ocorreu um erro ao abrir os slides.");
                }
            } else {
                System.out.println("Arquivo não existe ou não foi escolhido.");
            }
            ClassRoomUI classRoomUI = ClassRoomUI.getInstance();
            classRoomUI.setVisible(true);
            classRoomUI.computerOn();
            classRoomUI.theProfessor(p.getName());
        } else {
            ClassRoomUI classRoomUI = ClassRoomUI.getInstance();
            classRoomUI.setVisible(true);
            classRoomUI.computerOff();
        }
        return new DataObject(); // no particular info to return
    }

}
