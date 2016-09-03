/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartclass;

import context.arch.discoverer.Discoverer;
import context.arch.discoverer.query.AbstractQueryItem;
import context.arch.enactor.Enactor;
import context.arch.enactor.EnactorXmlParser;
import context.arch.widget.Widget;
import context.arch.widget.WidgetXmlParser;
import javax.swing.SwingUtilities;
import smartclass.enactor.RoomEnactor;
import smartclass.services.AirService;
import smartclass.services.ComputerService;
import smartclass.services.LightService;
import smartclass.services.ProjectorService;
import smartclass.ui.ClassRoomUI;
import smartclass.ui.ClassUI;

/**
 *
 * @author Pedro
 */
public class SmartClass {

    /*
 * Sala de aula inteligente, monitorando, no minimo, 3 elementos: projetor,
 * arcondicionado, computador. Colocar a exibicao dos slides de aula quando um
 * determinado professor entrar na sala. Deve considerar, no minimo, 4 professores e
 * as preferencias de cada professor quanto a temperatura do ar. Colocar os slides
 * especificos do professor que entrar na sala.
     */
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Discoverer.start();

        Widget roomWidget = WidgetXmlParser.createWidget("xml/room-widget.xml");
        Widget projectorWidget = WidgetXmlParser.createWidget("xml/projector-widget.xml");
        Widget computerWidget = WidgetXmlParser.createWidget("xml/computer-widget.xml");
        Widget lightWidget = WidgetXmlParser.createWidget("xml/light-widget.xml");
        Widget airWidget = WidgetXmlParser.createWidget("xml/air-widget.xml");

        LightService ls = new LightService(lightWidget);
        lightWidget.addService(ls);
        
        ProjectorService ps = new ProjectorService(projectorWidget);
        projectorWidget.addService(ps);
        
        ComputerService cs = new ComputerService(computerWidget);
        computerWidget.addService(cs);
        
        AirService as = new AirService(airWidget);
        airWidget.addService(as);

        AbstractQueryItem<?, ?> roomWidgetQuery = WidgetXmlParser.createWidgetSubscriptionQuery(roomWidget);
        AbstractQueryItem<?, ?> lightWidgetQuery = WidgetXmlParser.createWidgetSubscriptionQuery(lightWidget);
        AbstractQueryItem<?, ?> projectorWidgetQuery = WidgetXmlParser.createWidgetSubscriptionQuery(projectorWidget);
        AbstractQueryItem<?, ?> computerWidgetQuery = WidgetXmlParser.createWidgetSubscriptionQuery(computerWidget);
        AbstractQueryItem<?, ?> airWidgetQuery = WidgetXmlParser.createWidgetSubscriptionQuery(airWidget);

        RoomEnactor roomEnactorLight = new RoomEnactor(roomWidgetQuery, lightWidgetQuery, "light", "", "LightWidget");
        RoomEnactor roomEnactorProjector = new RoomEnactor(roomWidgetQuery, projectorWidgetQuery, "status", "", "ProjectorWidget");
        RoomEnactor roomEnactorComputer = new RoomEnactor(roomWidgetQuery, computerWidgetQuery, "status", "", "ComputerWidget");
        RoomEnactor roomEnactorAir = new RoomEnactor(roomWidgetQuery, airWidgetQuery, "status", "", "AirWidget");
        
        ClassUI classSensors = new ClassUI(roomWidget);
        classSensors.setVisible(true);

    }

}
