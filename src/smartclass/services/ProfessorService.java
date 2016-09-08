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
import smartclass.ui.ClassRoomUI;

/**
 *
 * @author Pedro
 */
public class ProfessorService extends Service{
    
    public ProfessorService(final Widget widget) {
        super(widget, "ProfessorService",
                new FunctionDescriptions() {
            {
                add(new FunctionDescription(
                        "professorControl",
                        "Represents de professor",
                        widget.getNonConstantAttributes()));
            }
        });
    }

    @Override
    public DataObject execute(ServiceInput si) {
        int temp = si.getInput().getAttributeValue("temperature");
        int slides = si.getInput().getAttributeValue("slides");
        int time = si.getInput().getAttributeValue("time");
        ClassRoomUI classRoomUI = ClassRoomUI.getInstance();
        classRoomUI.setVisible(true);
        System.out.println(temp+" "+slides+" "+time);
        classRoomUI.airTemp(temp);
        return new DataObject(); // no particular info to return
    }
    
}
