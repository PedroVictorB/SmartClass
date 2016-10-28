/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartclass;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import context.arch.discoverer.Discoverer;
import context.arch.discoverer.query.AbstractQueryItem;
import context.arch.enactor.Enactor;
import context.arch.enactor.EnactorXmlParser;
import context.arch.widget.Widget;
import context.arch.widget.WidgetXmlParser;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import smartclass.enactor.RoomEnactor;
import smartclass.entities.ContextResponses;
import smartclass.entities.ContextResponsesContainer;
import smartclass.services.AirService;
import smartclass.services.ComputerService;
import smartclass.services.LightService;
import smartclass.services.ProfessorService;
import smartclass.services.ProjectorService;
import smartclass.ui.ClassRoomUI;
import smartclass.ui.ClassUI;
import smartclass.ui.ProfessorUI;
import smartclass.util.NgsiRequest;

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

        int tipo = 1;

        if (tipo == 0) {
            Discoverer.start();

            Widget roomWidget = WidgetXmlParser.createWidget("xml/room-widget.xml");

            ClassUI classSensors = new ClassUI(roomWidget);
            classSensors.setVisible(true);

            Widget projectorWidget = WidgetXmlParser.createWidget("xml/projector-widget.xml");
            Widget computerWidget = WidgetXmlParser.createWidget("xml/computer-widget.xml");
            Widget lightWidget = WidgetXmlParser.createWidget("xml/light-widget.xml");
            Widget airWidget = WidgetXmlParser.createWidget("xml/air-widget.xml");
            Widget professorWidget = WidgetXmlParser.createWidget("xml/professor-widget.xml");

            LightService ls = new LightService(lightWidget);
            lightWidget.addService(ls);

            ProjectorService ps = new ProjectorService(projectorWidget);
            projectorWidget.addService(ps);

            ComputerService cs = new ComputerService(computerWidget);
            computerWidget.addService(cs);

            AirService as = new AirService(airWidget);
            airWidget.addService(as);

            ProfessorService profs = new ProfessorService(professorWidget);
            professorWidget.addService(profs);

            AbstractQueryItem<?, ?> roomWidgetQuery = WidgetXmlParser.createWidgetSubscriptionQuery(roomWidget);
            AbstractQueryItem<?, ?> lightWidgetQuery = WidgetXmlParser.createWidgetSubscriptionQuery(lightWidget);
            AbstractQueryItem<?, ?> projectorWidgetQuery = WidgetXmlParser.createWidgetSubscriptionQuery(projectorWidget);
            AbstractQueryItem<?, ?> computerWidgetQuery = WidgetXmlParser.createWidgetSubscriptionQuery(computerWidget);
            AbstractQueryItem<?, ?> airWidgetQuery = WidgetXmlParser.createWidgetSubscriptionQuery(airWidget);
            AbstractQueryItem<?, ?> professorWidgetQuery = WidgetXmlParser.createWidgetSubscriptionQuery(professorWidget);

            RoomEnactor roomEnactorLight = new RoomEnactor(roomWidgetQuery, lightWidgetQuery, "light", "", "LightWidget");
            RoomEnactor roomEnactorProjector = new RoomEnactor(roomWidgetQuery, projectorWidgetQuery, "status", "", "ProjectorWidget");
            RoomEnactor roomEnactorComputer = new RoomEnactor(roomWidgetQuery, computerWidgetQuery, "status", "", "ComputerWidget");
            RoomEnactor roomEnactorAir = new RoomEnactor(roomWidgetQuery, airWidgetQuery, "status", "", "AirWidget");
            RoomEnactor professorEnactorAir = new RoomEnactor(roomWidgetQuery, professorWidgetQuery, "status", "", "ProfessorWidget");

            ClassRoomUI classRoomUI = ClassRoomUI.getInstance();
            classRoomUI.setVisible(true);

            ProfessorUI professorUI = ProfessorUI.getInstance();
            professorUI.setVisible(true);
        } else if (tipo == 1) {
            NgsiRequest ng = new NgsiRequest();
            String bodyQuery = "{\n"
                    + "	\"entities\":[\n"
                    + "		{\n"
                    + "			\"type\": \"Sala\",\n"
                    + "			\"isPattern\": \"true\",\n"
                    + "			\"id\": \"SalaInteligente.*\"\n"
                    + "		}\n"
                    + "	]\n"
                    + "}";
            String resposta = ng.sendPost("/v1/queryContext", bodyQuery);

            ObjectMapper mapper = new ObjectMapper();

            try {
                ContextResponsesContainer t = mapper.readValue(resposta, ContextResponsesContainer.class);
                System.out.println(t.getContextResponses().length);
            } catch (IOException ex) {
                Logger.getLogger(SmartClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
