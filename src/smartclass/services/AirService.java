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
import smartclass.Professor;
import smartclass.ui.ClassRoomUI;
import smartclass.ui.ProfessorUI;

/**
 *
 * @author Pedro
 */
public class AirService extends Service{
    
    public AirService(final Widget widget) {
        super(widget, "AirService",
                new FunctionDescriptions() {
            {
                add(new FunctionDescription(
                        "airControl",
                        "Sets the air on or off",
                        widget.getNonConstantAttributes()));
            }
        });
    }

    @Override
    public DataObject execute(ServiceInput si) {
        int status = si.getInput().getAttributeValue("status");
        int temp = si.getInput().getAttributeValue("temperature");
        int time = si.getInput().getAttributeValue("time");
        if (status == 1) {
            ProfessorUI professorUI = ProfessorUI.getInstance();
            Professor p = professorUI.getProfessorAttributes(professorUI.getProfessorOfTheTime(time));
            ClassRoomUI classRoomUI = ClassRoomUI.getInstance();
            classRoomUI.setVisible(true);
            if(temp < p.getTemperature()){
                classRoomUI.airOff();
            }else{
                classRoomUI.airOn(p.getTemperature());
            }
        } else {
            ClassRoomUI classRoomUI = ClassRoomUI.getInstance();
            classRoomUI.setVisible(true);
            classRoomUI.airOff();
        }
        return new DataObject(); // no particular info to return
    }
}
