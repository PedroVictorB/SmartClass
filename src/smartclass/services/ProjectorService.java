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
public class ProjectorService extends Service{
    
    public ProjectorService(final Widget widget) {
        super(widget, "ProjectorService",
                new FunctionDescriptions() {
            {
                add(new FunctionDescription(
                        "projectorControl",
                        "Sets the projector on or off",
                        widget.getNonConstantAttributes()));
            }
        });
    }

    @Override
    public DataObject execute(ServiceInput si) {
        int status = si.getInput().getAttributeValue("status");
        
        if (status == 1) {
            ClassRoomUI classRoomUI = ClassRoomUI.getInstance();
            classRoomUI.setVisible(true);
            classRoomUI.projectorOn();
        } else {
            ClassRoomUI classRoomUI = ClassRoomUI.getInstance();
            classRoomUI.setVisible(true);
            classRoomUI.projectorOff();
        }
        return new DataObject(); // no particular info to return
    }
    
}
