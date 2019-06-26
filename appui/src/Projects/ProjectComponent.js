import React, { Component } from "react";
import { translate, Trans } from 'react-i18next';
import {Row} from "reactstrap";
import { Button } from "reactstrap";
import Moment from 'react-moment';
import EndDateComponent from "./EndDate/EndDateComponent";
import SponsorshipComponent from "./Sponsorship/SponsorshipComponent";
import EmployeesComponent from "./Employees/EmployeesComponent";
import TasksComponent from "./Tasks/TasksComponent";
import ProjectLeaderComponent from "./ProjectLeader/ProjectLeaderComponent";

class ProjectComponent extends Component{
    constructor(props){
        super(props)
        this.state={
            projectLeader: "",
            numberOfEmployees: 0,
            numberOfTasksDone: 0,
            numberOfAllTasks: 0,
            numberOfDelayed: 0,
            unassignedFunds: 0,
            listOfEmployees: [],
            listOfOtherEmployees: [],
            listOfTasks: [],
            listOfSponsorhips: []
        }
        this.exportFile = this.exportFile.bind(this);
    }

    exportFile(){
            let filename = "export.json";
            let contentType = "application/json;charset=utf-8;";
            if (window.navigator && window.navigator.msSaveOrOpenBlob) {
                var blob = new Blob([decodeURIComponent(encodeURI(JSON.stringify(this.props.wholeProject)))], { type: contentType });
                navigator.msSaveOrOpenBlob(blob, filename);
            } else {
                var a = document.createElement('a');
                a.download = filename;
                a.href = 'data:' + contentType + ',' + encodeURIComponent(JSON.stringify(this.props.wholeProject));
                a.target = '_blank';
                document.body.appendChild(a);
                a.click();
                document.body.removeChild(a);
            }
    }

    componentDidMount(): void {
        const URL = "http://localhost:8080"
        fetch(URL + "/projects/" + this.props.projectId + "/leader",{
            headers: {
                'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
            }})
            .then((response) => {
                if(!response.ok) throw new Error(response.status);
                else return response.json();
            })
            .then(data => {
                this.setState({ projectLeader: data.realname })
            })
            .catch((error) => {
                console.error(error);
            });
        fetch(URL + "/projects/" + this.props.projectId + "/employees/count",{
            headers: {
                'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
            }})
            .then((response) => {
                if(!response.ok) throw new Error(response.status);
                else return response.json();
            })
            .then(data => {
                this.setState({ numberOfEmployees: data })
            })
            .catch((error) => {
                console.error(error);
            });
        fetch(URL + "/projects/" + this.props.projectId + "/tasks/done/count",{
            headers: {
                'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
            }})
            .then((response) => {
                if(!response.ok) throw new Error(response.status);
                else return response.json();
            })
            .then(data => {
                if(data!==0){this.setState({ numberOfTasksDone: data })}
            })
            .catch((error) => {
                console.error(error);
            });
        fetch(URL + "/projects/" + this.props.projectId + "/tasks/count",{
            headers: {
                'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
            }})
            .then((response) => {
                if(!response.ok) throw new Error(response.status);
                else return response.json();
            })
            .then(data => {
                this.setState({ numberOfAllTasks: data })
            })
            .catch((error) => {
                console.error(error);
            });
        fetch(URL + "/projects/" + this.props.projectId + "/tasks/delayed/count",{
            headers: {
                'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
            }})
            .then((response) => {
                if(!response.ok) throw new Error(response.status);
                else return response.json();
            })
            .then(data => {
                this.setState({ numberOfDelayed: data })
            })
            .catch((error) => {
                console.error(error);
            });
        fetch(URL + "/projects/" + this.props.projectId + "/sponsorship/unassigned",{
            headers: {
                'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
            }})
            .then((response) => {
                if(!response.ok) throw new Error(response.status);
                else return response.json();
            })
            .then(data => {
                this.setState({ unassignedFunds: data })
            })
            .catch((error) => {
                console.error(error);
            });
        fetch(URL + "/projects/" + this.props.projectId + "/employees",{
            headers: {
                'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
            }})
            .then((response) => {
                if(!response.ok) throw new Error(response.status);
                else return response.json();
            })
            .then(data => {
                this.setState({ listOfEmployees: data })
            })
            .catch((error) => {
                console.error(error);
            });
        fetch(URL + "/projects/" + this.props.projectId + "/employees/other",{
            headers: {
                'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
            }})
            .then((response) => {
                if(!response.ok) throw new Error(response.status);
                else return response.json();
            })
            .then(data => {
                this.setState({ listOfOtherEmployees: data })
            })
            .catch((error) => {
                console.error(error);
            });
        fetch(URL + "/projects/" + this.props.projectId + "/tasks",{
            headers: {
                'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
            }})
            .then((response) => {
                if(!response.ok) throw new Error(response.status);
                else return response.json();
            })
            .then(data => {
                this.setState({ listOfTasks: data })
            })
            .catch((error) => {
                console.error(error);
            });
        fetch(URL + "/projects/" + this.props.projectId + "/sponsorship",{
            headers: {
                'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
            }})
            .then((response) => {
                if(!response.ok) throw new Error(response.status);
                else return response.json();
            })
            .then(data => {
                this.setState({ listOfSponsorhips: data })
            })
            .catch((error) => {
                console.error(error);
            });
    }

    render(){
        return(
            <div>
                <h3>
                    {this.props.title} {<img style={{cursor:'pointer'}} src={"./img/export.svg"} width={20} height={20} onClick={() => {
                    this.exportFile()}}/>}
                    <div className="text-right">
                        <Button size="xs" style={{color: "#9F6000",
                            background: "#FEEFB3",
                            border:"1px solid #9F6000",
                            borderRadius:"25%"}}
                       >
                            {this.props.t('buttons.stop', { framework: "react-i18next" })}
                        </Button>
                        <Button size="xs" style={{color: "#D8000C",
                            background: "#FFD2D2",
                            border:"1px solid #D8000C",
                        borderRadius:"25%"}}>
                            {this.props.t('buttons.end', { framework: "react-i18next" })}
                        </Button>
                    </div>
                </h3>
                <div>
                    <Row>
                        {this.props.t('project.startDate', { framework: "react-i18next" })}<Moment format="YYYY-MM-DD HH:mm">
                        {this.props.startDate}
                    </Moment>
                    </Row>
                    <Row>
                        <EndDateComponent endDate={this.props.endDate}
                                          projectId={this.props.projectId}
                                          buttonStyle={this.props.buttonStyle}
                                          credentials={this.props.credentials}
                        />
                    </Row>
                    <Row>
                        <SponsorshipComponent sponsorship={this.props.sponsorship}
                                              listOfSponsorships={this.state.listOfSponsorhips}
                                              unassignedFunds={this.state.unassignedFunds}
                                              projectId={this.props.projectId}
                                              buttonStyle={this.props.buttonStyle}
                                              credentials={this.props.credentials}

                        />
                    </Row>
                    <Row>
                        <ProjectLeaderComponent projectId={this.props.projectId}
                                                creatorId={this.props.user}
                                                buttonStyle={this.props.buttonStyle}
                                                credentials={this.props.credentials}

                        />
                    </Row>
                    <Row>
                        <EmployeesComponent numberOfEmployees={this.state.numberOfEmployees}
                                            listOfEmployees={this.state.listOfEmployees}
                                            listOfOtherEmployees={this.state.listOfOtherEmployees}
                                            project={this.props.name}
                                            buttonStyle={this.props.buttonStyle}
                                            credentials={this.props.credentials}
                                            projectId2={this.props.projectId2}
                                            user={this.props.user}
                                            projectId={this.props.projectId}
                        />
                    </Row>
                    <Row>
                        <TasksComponent
                            numberOfTasksDone={this.state.numberOfTasksDone}
                            numberOfAllTasks={this.state.numberOfAllTasks}
                            listOfTasks={this.state.listOfTasks}
                            numberOfDelayed={this.state.numberOfDelayed}
                            projectId2={this.props.projectId2}
                            buttonStyle={this.props.buttonStyle}
                            credentials={this.props.credentials}
                            user={this.props.user}
                            projectId={this.props.projectId}
                        />
                    </Row>
                </div>
            </div>
        )
    }
}
export default translate('common')(ProjectComponent);