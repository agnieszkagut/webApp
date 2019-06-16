import React, { Component } from "react";
import { translate, Trans } from 'react-i18next';
import EmployeeItem from "./EmployeeItem";

class EmployeesListComponent extends Component{
    render(){
        return(
            <ul>
                {this.props.listOfEmployees.map(function(employee,index) {
                        return (
                            <div key={index}>
                                <EmployeeItem
                                    key={index}
                                    realname={employee.realname}
                                    position={employee.position}
                                />
                            </div>
                        )
                    }
                )}
            </ul>
        )
    }
}

export default translate('common')(EmployeesListComponent);