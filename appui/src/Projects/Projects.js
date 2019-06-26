import React, { Component } from "react";
import { translate, Trans } from 'react-i18next';
import ProjectComponent from "./ProjectComponent";
import NewProjectForm from "./NewProject/NewProjectForm";
import ProgramStatus from "./ProgramStatus";
import NewProjectFile from "./NewProject/NewProjectFile";
import Media from 'react-media';

class Projects extends  Component{
    constructor(props){
        super(props)
        this.state={
            projects: [],
            showProjectForm: false,
            showProjectFile: false,
            projectId: 0
        }
        this.myCallback = this.myCallback.bind(this);
        this.loadFromApi = this.loadFromApi.bind(this);
    }
    loadFromApi=()=>{
        const URL = "http://localhost:8080"
        fetch(URL + "/projects",{
            headers: {
                'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
            }
        }).then(res => res.json())
            .then(data => {
                this.setState({ projects: data })
            });
        if(this.props.credentials.userLevel===2){
            console.log(this.props.user)
            fetch(URL + "/projects/leader/" + this.props.user,{
                headers: {
                    'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
                }
            }).then(res => res.json())
                .then(data => {
                    console.log(data)
                    this.setState({ projectId: data.projectId })
                });
        }
    }
    componentDidMount() {
        this.loadFromApi()
    }
    myCallback = () => {
        this.setState({ showProjectForm: !this.state.showProjectForm })
        this.props.refresh(1)
    }
    render(){
        const credentials = this.props.credentials
        const userId = this.props.user
        const projects = this.state.projects
        const buttonStyle=this.props.buttonStyle
        const projectId2= this.state.projectId
        return (
            <div>
                <h1>
                    {this.props.t('projects', { framework: "react-i18next" })}
                </h1>
                <div>
                    <ProgramStatus credentials={credentials}/>
                </div>
                {this.state.showProjectForm
                    ? (this.state.showProjectFile
                            ? <NewProjectForm callbackFromParent={this.myCallback}
                                              buttonStyle={buttonStyle}
                                              credentials={credentials}
                            />
                            : <NewProjectFile callbackFromParent={this.myCallback}
                                              buttonStyle={buttonStyle}
                                              credentials={credentials}
                            />
                    )
                    : <Media query="(min-width: 449px)">
                        {matches =>
                            matches
                                ?<ul style={{display: 'inline-flex', position: 'relative'}}>
                                    {this.state.projects.map(function (project, index) {
                                            return (
                                                <div style={{margin: '25px'}}
                                                     key={index}>
                                                    <ProjectComponent
                                                        key={index}
                                                        projectId={project.projectId}
                                                        user={userId}
                                                        title={project.name}
                                                        startDate={project.startDate}
                                                        endDate={project.endDate}
                                                        sponsorship={project.sponsorship}
                                                        name={project.name}
                                                        buttonStyle={buttonStyle}
                                                        credentials={credentials}
                                                        projectId2={projectId2}
                                                        wholeProject={project}
                                                    />
                                                </div>
                                            )
                                        }
                                    )}
                                    <div>
                                        {(this.props.credentials.userLevel === 1 || this.props.credentials.userLevel === 0)
                                            ? <button className="btn btn-default" onClick={() => {
                                                this.setState({showProjectForm: !this.state.showProjectForm})
                                                this.setState({showProjectFile: true})
                                            }}>
                                                <i className="fas fa-plus-circle"></i>
                                                {this.props.t('project.add', {framework: "react-i18next"})}
                                            </button>
                                            : null
                                        }
                                        {(this.props.credentials.userLevel === 1 || this.props.credentials.userLevel === 0)
                                            ? <button className="btn btn-default" onClick={() => {
                                                this.setState({showProjectForm: !this.state.showProjectForm})
                                                this.setState({showProjectFile: false})
                                            }}>
                                                <img src={"./img/import.svg"} width={20} height={20}/>
                                                {this.props.t('project.addFromFile', {framework: "react-i18next"})}
                                            </button>
                                            : null
                                        }
                                    </div>
                                </ul>
                                :<ul style={{display: 'list-item', position: 'relative'}}>
                                    {this.state.projects.map(function (project, index) {
                                            return (
                                                <div style={{margin: '25px'}}
                                                     key={index}>
                                                    <ProjectComponent
                                                        key={index}
                                                        projectId={project.projectId}
                                                        user={userId}
                                                        title={project.name}
                                                        startDate={project.startDate}
                                                        endDate={project.endDate}
                                                        sponsorship={project.sponsorship}
                                                        name={project.name}
                                                        buttonStyle={buttonStyle}
                                                        credentials={credentials}
                                                        projectId2={projectId2}
                                                        wholeProject={project}
                                                    />
                                                </div>
                                            )
                                        }
                                    )}
                                    <div>
                                        {(this.props.credentials.userLevel === 1 || this.props.credentials.userLevel === 0)
                                            ? <button className="btn btn-default" onClick={() => {
                                                this.setState({showProjectForm: !this.state.showProjectForm})
                                                this.setState({showProjectFile: true})
                                            }}>
                                                <i className="fas fa-plus-circle"></i>
                                                {this.props.t('project.add', {framework: "react-i18next"})}
                                            </button>
                                            : null
                                        }
                                        {(this.props.credentials.userLevel === 1 || this.props.credentials.userLevel === 0)
                                            ? <button className="btn btn-default" onClick={() => {
                                                this.setState({showProjectForm: !this.state.showProjectForm})
                                                this.setState({showProjectFile: false})
                                            }}>
                                                <img src={"./img/import.svg"} width={20} height={20}/>
                                                {this.props.t('project.addFromFile', {framework: "react-i18next"})}
                                            </button>
                                            : null
                                        }
                                    </div>
                                </ul>
                        }
                    </Media>
                }
            </div>
        )
    }
}

export default translate('common')(Projects);