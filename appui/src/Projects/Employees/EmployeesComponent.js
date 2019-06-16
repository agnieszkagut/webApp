import React, { Component } from "react";
import { translate, Trans } from 'react-i18next';
import EmployeesListComponent from "./EmployeesListComponent";
import AddSponsorshipsComponent from "../Sponsorship/SponsorshipComponent";
import EditUserProjectComponent from "./EditUserProjectComponent";

class EmployeesComponent extends Component{
    constructor(props){
        super(props)
        this.state={
            showEmployees: false,
            showEdit: false
        }
    }
    render(){
        return(
            <div>
                <div>
                    {this.props.t('project.employees', { framework: "react-i18next" })}{this.props.numberOfEmployees}
                    <i className="fas fa-chevron-down" onClick={()=> this.setState({showEmployees: !this.state.showEmployees})}></i>
                </div>
                <div>
                {this.state.showEmployees
                        ?<EmployeesListComponent listOfEmployees={this.props.listOfEmployees}/>
                        :null
                }
                </div>
                <div>
                    {(this.props.credentials.userLevel === 1 || this.props.credentials.userLevel === 0 || ((this.props.credentials.userLevel === 2) && (this.props.projectId == this.props.projectId2)))
                        ?<button className={this.props.buttonStyle} onClick={()=> this.setState({showEdit: !this.state.showEdit})}>
                            {this.props.t('buttons.edit', { framework: "react-i18next" })}
                        </button>
                        :null
                    }
                </div>
                <div>
                    {this.state.showEdit
                        ?<EditUserProjectComponent users={this.props.listOfOtherEmployees}
                                                   project={this.props.name}
                                                   buttonStyle={this.props.buttonStyle}
                                                   credentials={this.props.credentials}
                        />
                        :null
                    }
                </div>
            </div>
        )
    }
}

export default translate('common')(EmployeesComponent);